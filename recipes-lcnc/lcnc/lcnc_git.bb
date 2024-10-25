LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://github.com/LinuxCNC/linuxcnc.git;protocol=https;branch=master \
           file://0002-configure.ac-Add-BUILD_TKINTER-flag.patch \
           file://0001-configure.ac-Check-PATH-for-TCLCONFIG-as-well-as-lib.patch \
           "

inherit autotools-brokensep pkgconfig systemd python3native

# Modify these as desired
PV = "2.10.0~pre0+git"
SRCREV = "8102217123f319c0fee7403f48448bd0844ccbcb"

S = "${WORKDIR}/git/src"

DEPENDS += "libtirpc systemd libusb1 glib-2.0 gtk+3 python3-yapps2-native intltool-native boost boost-native python3 python3-native tcl tk xinerama readline libglu libxmu"

EXTRA_OECONF += "--without-libmodbus --disable-check-runtime-deps --disable-gtk --disable-tkinter --with-boost-python=boost_python312"

CONFIGUREOPTS:remove = "--disable-dependency-tracking"
CONFIGUREOPTS:remove = "--disable-silent-rules"
CONFIGUREOPTS:remove = "--with-libtool-sysroot"
EXTRA_OECONF:remove = "--disable-static"

python do_display_banner() {
    bb.plain("***********************************************");
    bb.plain("*                                             *");
    bb.plain("*             Building LinuxCNC               *");
    bb.plain("*                                             *");
    bb.plain("***********************************************");
}

addtask display_banner before do_build