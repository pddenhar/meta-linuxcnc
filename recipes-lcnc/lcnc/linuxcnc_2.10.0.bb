LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://github.com/LinuxCNC/linuxcnc.git;protocol=https;branch=master \
           file://0002-configure.ac-Add-BUILD_TKINTER-flag.patch \
           file://0001-configure.ac-Check-PATH-for-TCLCONFIG-as-well-as-lib.patch \
           file://0001-Un-ruin-the-ridiculous-shebang-substitution-for-pyth.patch;patchdir=.. \
           "

inherit autotools-brokensep pkgconfig systemd python3native

# Modify these as desired
PV = "2.10.0~pre0+git"
SRCREV = "8102217123f319c0fee7403f48448bd0844ccbcb"

S = "${WORKDIR}/git/src"

DEPENDS += "libtirpc systemd libusb1 glib-2.0 gtk+3 python3-yapps2-native intltool-native \
boost boost-native python3 python3-native tcl tk xinerama readline libglu libxmu \
asciidoc-native groff-native" 
# These ones were missing from configure.ac checks

RDEPENDS:${PN} += "tcl tk python3-core bash"

EXTRA_OECONF += "--without-libmodbus --disable-check-runtime-deps --disable-gtk --disable-tkinter --with-boost-python=boost_python312"

CONFIGUREOPTS:remove = "--disable-dependency-tracking"
CONFIGUREOPTS:remove = "--disable-silent-rules"
CONFIGUREOPTS:remove = "--with-libtool-sysroot"
EXTRA_OECONF:remove = "--disable-static"

# Configure some extra packaged files for the base package
FILES:${PN}:append = " ${libdir}/${PYTHON_PN}/dist-packages/* "
# This one would maybe be better done as ${libdir}/${PYTHON_DIR}/dist-packages/
# but automake isn't getting the path right 

# Get rid of the files we don't want to package
do_install:append() { 
    rm -rf ${D}${libdir}/tcltk
    rm -rf ${D}${datadir}/gtksourceview-4
    rm -rf ${D}${datadir}/gscreen/
    rm -rf ${D}${datadir}/axis/
    rm -rf ${D}${datadir}/glade/
    rm -rf ${D}${datadir}/qtvcp/
    rm -rf ${D}${datadir}/gmoccapy/


}

python do_display_banner() {
    bb.plain("***********************************************");
    bb.plain("*                                             *");
    bb.plain("*             Building LinuxCNC               *");
    bb.plain("*                                             *");
    bb.plain("***********************************************");
}

addtask display_banner before do_build