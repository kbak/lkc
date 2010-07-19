module Main where

import Graphics.UI.Gtk
import Graphics.UI.Gtk.Glade
import Control.Concurrent

main :: IO ()
main = do
    initGUI
    timeoutAddFull (yield >> return True) priorityDefaultIdle 50
    Just xml <- xmlNew "gui.glade"
    runWelcome xml
    mainGUI

runWelcome :: GladeXML -> IO ()
runWelcome xml = do
    wndWelcome   <- xmlGetWidget xml castToWindow "wndWelcome"
    onDestroy wndWelcome mainQuit
    btnCancel <- xmlGetWidget xml castToButton "btnCancel1"
    onClicked btnCancel $ do
        widgetDestroy wndWelcome
    btnNext <- xmlGetWidget xml castToButton "btnNext"
    onClicked btnNext $ do
        rbtnStartNew <- xmlGetWidget xml castToRadioButton "rbtnStartNew"
        isActive <- toggleButtonGetActive rbtnStartNew
        if isActive
            then do
                widgetHide wndWelcome
                runInspect xml
            else do
                fileChooser <- xmlGetWidget xml castToFileChooserDialog "fcdChooseFile"
                btnFileOk <- xmlGetWidget xml castToButton "btnFileOk"
                btnFileCancel <- xmlGetWidget xml castToButton "btnFileCancel"
                onClicked btnFileOk $ do
                    widgetHide fileChooser
                    widgetHide wndWelcome
                    file <- fileChooserGetURI fileChooser
                    putStrLn $ show file
                    -- run config
                onClicked btnFileCancel $ do
                    widgetHide fileChooser
                dialogRun fileChooser
                return ()
    widgetShowAll wndWelcome

runInspect xml = do
    wndInspect   <- xmlGetWidget xml castToWindow "wndInspect"
    onDestroy wndInspect mainQuit
    btnCancel <- xmlGetWidget xml castToButton "btnCancel"
    onClicked btnCancel $ do
        widgetDestroy wndInspect
    tbtnInfo <- xmlGetWidget xml castToToggleButton "tbtnInfo"
    onToggled tbtnInfo $ do
        tvConsole <- xmlGetWidget xml castToTextView "tvConsole"
        sepInspect <- xmlGetWidget xml castToHSeparator "sepInspect"
        btnCopy <- xmlGetWidget xml castToButton "btnCopy"
        isActive <- toggleButtonGetActive tbtnInfo
        if isActive
            then do
                widgetShow tvConsole
                widgetShow sepInspect
                widgetShow btnCopy
            else do
                widgetHide tvConsole
                widgetHide sepInspect
                widgetHide btnCopy
    widgetShowAll wndInspect

    forkIO $ do
        dumpConfiguration xml
        detectHardware xml
    return ()
--        widgetDestroy wndInspect mainQuit

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
