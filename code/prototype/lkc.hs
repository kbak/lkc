module Main where

import Graphics.UI.Gtk
import Graphics.UI.Gtk.Glade
import Control.Concurrent

main = do
    initGUI
    timeoutAddFull (yield >> return True) priorityDefaultIdle 50
    Just xml <- xmlNew "gui.glade"
    runStart xml
    mainGUI

runStart xml = do
    wndStart   <- xmlGetWidget xml castToWindow "wndStart"
    onDestroy wndStart mainQuit
    btnCancel <- xmlGetWidget xml castToButton "btnCancel1"
    onClicked btnCancel $ do
        widgetDestroy wndStart
    btnNext <- xmlGetWidget xml castToButton "btnNext"
    onClicked btnNext $ do
        rbtnStartNew <- xmlGetWidget xml castToRadioButton "rbtnStartNew"
        isActive <- toggleButtonGetActive rbtnStartNew
        if isActive
            then do
                widgetHide wndStart
                runSplash xml
            else do
                fileChooser <- xmlGetWidget xml castToFileChooserDialog "fcdChooseFile"
                btnFileOk <- xmlGetWidget xml castToButton "btnFileOk"
                btnFileCancel <- xmlGetWidget xml castToButton "btnFileCancel"
                onClicked btnFileOk $ do
                    widgetHide fileChooser
                    widgetHide wndStart
                    file <- fileChooserGetURI fileChooser
                    putStrLn $ show file
                    -- run config
                onClicked btnFileCancel $ do
                    widgetHide fileChooser
                dialogRun fileChooser
                return ()
    widgetShowAll wndStart

runSplash xml = do
    wndStartNew   <- xmlGetWidget xml castToWindow "wndStartNew"
    onDestroy wndStartNew mainQuit
    btnCancel <- xmlGetWidget xml castToButton "btnCancel"
    onClicked btnCancel $ do
        widgetDestroy wndStartNew
    tbtnInfo <- xmlGetWidget xml castToToggleButton "tbtnInfo"
    onToggled tbtnInfo $ do
        entOutput <- xmlGetWidget xml castToEntry "entOutput"
        sepSplash <- xmlGetWidget xml castToHSeparator "sepSplash"
        btnCopy <- xmlGetWidget xml castToButton "btnCopy"
        isActive <- toggleButtonGetActive tbtnInfo
        (width, _) <- windowGetSize wndStartNew
        if isActive
            then do
                widgetShow entOutput
                widgetShow sepSplash
                widgetShow btnCopy
            else do
                widgetHide entOutput
                widgetHide sepSplash
                widgetHide btnCopy
        windowResize wndStartNew width $ -1
    widgetShowAll wndStartNew
    forkIO $ do
        dumpConfiguration xml
        detectHardware xml
    return ()
--        widgetDestroy wndStartNew
  --      mainQuit

taskTime = 1000 * 10

nPartsConfiguration = 2

nPartsHardware = 300

nParts = nPartsConfiguration + nPartsHardware

dumpConfiguration xml = do
    workAndUpdate xml "imgWaitConfiguration" "imgDoneConfiguration" $
                  doSomething nPartsConfiguration

detectHardware xml = do
    workAndUpdate xml "imgWaitHardware" "imgDoneHardware" $
                  doSomething nPartsHardware

doSomething nParts progressBar = do
    sequence $ concat $ replicate nParts
        [threadDelay taskTime, progressBarIncrement progressBar]

workAndUpdate xml waitStr doneStr f = do
    imgWait <- xmlGetWidget xml castToImage waitStr
    widgetShow imgWait
    progressBar <- xmlGetWidget xml castToProgressBar "progressbar"
    f progressBar
    imgDone <- xmlGetWidget xml castToImage doneStr
    widgetHide imgWait
    widgetShow imgDone

progressBarIncrement progressBar = do
    fr <- progressBarGetFraction progressBar
    progressBarSetFraction progressBar (fr + 1 / fromIntegral nParts)