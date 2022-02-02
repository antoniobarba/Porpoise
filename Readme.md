ANNOUNCEMENT (For new users):

=============================

I officially announce that I will be stopping any further development on BOTH projects and calling it quits to focus on University. Sorry to everyone that held in here for further development on |MMJR2| and for possible fixes down the line for |MMJR| .... but it really is too much for me to handle right now.

Both versions are on github and I encourage determined or curious people to use the source code. However at the rate that Dolphin Official is going, down the line you might not need |MMJR| or definitely not |MMJR2| anymore. I might have time to push any PR's over to Dolphin Official from |MMJR2| for QOL stuff like the theme changer or maybe the quick menu but like I said everything is on github and free to access so anyone can do it if in case I can't. As for whether I will come back and tackle this project again ... I have no clue; I might still try to work on it little by little over time but as it stands I'm officially too tied down with IRL stuff to handle the projects anymore.

So special thanks to @Lynx for his godly help and contributions and the testers; especially @Gamer 64 for his assistance over the recent months. As well as to @Jos for the great updates, knowledge and development over at Dolphin Official (https://dolphin-emu.org/download/). Thanks to everyone for supporting the app, development and my endeavors in learning coding from scratch.

=================================


# Dolphin MMJR2
An Android-only performance-focused dolphin fork, rebased on top of latest dolphin development builds and reimplementing MMJ UX and performance improvements, plus adding our own.

Grab the latest build in the [releases](https://github.com/Bankaimaster999/Dolphin-MMJR/releases) section, or check for new version in the in-app updater. Old MMJR v1.0 builds can be found at the old repository [here](https://github.com/Bankaimaster999/dolphin/releases). 1.0 and 2.0 builds can be installed without conflicts as they use different folders, but **savestates are not compatible**. We kindly ask you to avoid misusing GitHub Issues and Pull Requests.

This fork wouldn't be possible without the crazy amount of work that developers much more skilled than us put into Dolphin.

Join the MMJR community at our [Discord](https://discord.gg/NZTQRpy5B3).

## Dolphin - A GameCube and Wii Emulator

[Homepage](https://dolphin-emu.org/) | [Project Site](https://github.com/dolphin-emu/dolphin) | [Buildbot](https://dolphin.ci) | [Forums](https://forums.dolphin-emu.org/) | [Wiki](https://wiki.dolphin-emu.org/) | [Issue Tracker](https://bugs.dolphin-emu.org/projects/emulator/issues) | [Coding Style](https://github.com/dolphin-emu/dolphin/blob/master/Contributing.md) | [Transifex Page](https://www.transifex.com/projects/p/dolphin-emu/)

Dolphin is an emulator for running GameCube and Wii games on Windows,
Linux, macOS, and recent Android devices. It's licensed under the terms
of the GNU General Public License, version 2 or later (GPLv2+).

Please read the [FAQ](https://dolphin-emu.org/docs/faq/) before using Dolphin.

## System Requirements

### Android

* OS
    * Android 5.0 Lollipop or higher (SDK >= 21).
* Processor
    * A processor with support for 64-bit applications (either ARMv8 or x86-64).
* Graphics
    * A graphics processor that supports OpenGL ES 3.0 or higher. Performance varies heavily with [driver quality](https://dolphin-emu.org/blog/2013/09/26/dolphin-emulator-and-opengl-drivers-hall-fameshame/).
    * A graphics processor that supports standard desktop OpenGL features is recommended for best performance.

Dolphin can only be installed on devices that satisfy the above requirements. Attempting to install on an unsupported device will fail and display an error message.

## Building for Android

These instructions assume familiarity with Android development. If you do not have an
Android dev environment set up, see [AndroidSetup.md](AndroidSetup.md).

If using Android Studio, import the Gradle project located in `./Source/Android`.

Android apps are compiled using a build system called Gradle. Dolphin's native component,
however, is compiled using CMake. The Gradle script will attempt to run a CMake build
automatically while building the Java code.

## Uninstalling

When Dolphin has been installed with the NSIS installer, you can uninstall
Dolphin like any other Windows application.

Linux users can run `cat install_manifest.txt | xargs -d '\n' rm` as root from the build directory
to uninstall Dolphin from their system.

macOS users can simply delete Dolphin.app to uninstall it.

Additionally, you'll want to remove the global user directory (see below to
see where it's stored) if you don't plan to reinstall Dolphin.

## Command Line Usage

`Usage: Dolphin [-h] [-d] [-l] [-e <str>] [-b] [-v <str>] [-a <str>]`

* -h, --help Show this help message
* -d, --debugger Show the debugger pane and additional View menu options
* -l, --logger Open the logger
* -e, --exec=<str> Load the specified file (DOL,ELF,WAD,GCM,ISO)
* -b, --batch Exit Dolphin with emulator
* -v, --video_backend=<str> Specify a video backend
* -a, --audio_emulation=<str> Low level (LLE) or high level (HLE) audio

Available DSP emulation engines are HLE (High Level Emulation) and
LLE (Low Level Emulation). HLE is faster but less accurate whereas
LLE is slower but close to perfect. Note that LLE has two submodes (Interpreter and Recompiler)
but they cannot be selected from the command line.

Available video backends are "D3D" and "D3D12" (they are only available on Windows), "OGL", and "Vulkan".
There's also "Null", which will not render anything, and
"Software Renderer", which uses the CPU for rendering and
is intended for debugging purposes only.

## Sys Files

* `wiitdb.txt`: Wii title database from [GameTDB](https://www.gametdb.com/)
* `totaldb.dsy`: Database of symbols (for devs only)
* `GC/font_western.bin`: font dumps
* `GC/font_japanese.bin`: font dumps
* `GC/dsp_coef.bin`: DSP dumps
* `GC/dsp_rom.bin`: DSP dumps
* `Wii/clientca.pem`: Wii network certificate
* `Wii/clientcakey.pem`: Wii network certificate key
* `Wii/rootca.pem`: Wii network certificate issuer / CA

The DSP dumps included with Dolphin have been written from scratch and do not
contain any copyrighted material. They should work for most purposes, however
some games implement copy protection by checksumming the dumps. You will need
to dump the DSP files from a console and replace the default dumps if you want
to fix those issues.

Wii network certificates must be extracted from a Wii IOS. A guide for that can be found [here](https://wiki.dolphin-emu.org/index.php?title=Wii_Network_Guide).

## Folder Structure

These folders are installed read-only and should not be changed:

* `GameSettings`: per-game default settings database
* `GC`: DSP and font dumps
* `Maps`: symbol tables (dev only)
* `Shaders`: post-processing shaders
* `Themes`: icon themes for GUI
* `Resources`: icons that are theme-agnostic
* `Wii`: default Wii NAND contents

## Custom Textures

Custom textures have to be placed in the user directory under
`Load/Textures/[GameID]/`. You can find the Game ID by right-clicking a game
in the ISO list and selecting "ISO Properties".
