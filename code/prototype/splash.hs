module Main where

import Graphics.UI.Gtk
import Graphics.UI.Gtk.Glade
import Control.Concurrent

main = do
    initGUI
    timeoutAddFull (yield >> return True) priorityDefaultIdle 50
    Just xml <- xmlNew "splash.glade"
    window   <- xmlGetWidget xml castToWindow "windowMain"
    onDestroy window mainQuit
    closeButton <- xmlGetWidget xml castToButton "btnCancel"
    onClicked closeButton $ do
        widgetDestroy window
    widgetShowAll window
    forkIO $ do
        dumpConfiguration xml
        detectHardware xml
    mainGUI

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
        [threadDelay $ taskTime, progressBarIncrement progressBar]

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