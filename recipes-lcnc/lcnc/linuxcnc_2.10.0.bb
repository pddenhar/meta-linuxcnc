LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://github.com/LinuxCNC/linuxcnc.git;protocol=https;branch=master \
           file://0002-configure.ac-Add-BUILD_TKINTER-flag.patch \
           file://0001-configure.ac-Check-PATH-for-TCLCONFIG-as-well-as-lib.patch \
           file://0001-Un-ruin-the-ridiculous-shebang-substitution-for-pyth.patch;patchdir=.. \
           file://0001-Makefile-Patch-to-use-usr-bin-env-for-Python-and-TCL.patch;patchdir=.. \
           file://0001-Makefile-Add-LDFLAGS-to-panelui-and-module_helper-li.patch;patchdir=.. \
           file://0001-Autotools-Do-not-hard-code-executable-paths-at-compi.patch;patchdir=.. \
           file://use-standard-sitepy.patch \
           "

inherit autotools-brokensep pkgconfig systemd python3native

# Modify these as desired
PV = "2.10.0~pre0+git"
SRCREV = "8102217123f319c0fee7403f48448bd0844ccbcb"

S = "${WORKDIR}/git/src"

DEPENDS += "libtirpc systemd libusb1 glib-2.0 gtk+3 python3-yapps2-native intltool-native \
boost boost-native python3 python3-native tcl tcl-native tk xinerama readline libglu libxmu \
asciidoc-native groff-native" 
# These ones were missing from configure.ac checks

# tk provides wish, tk-lib provides libtk8.6.so
RDEPENDS:${PN} += "tcl tk tk-lib python3-core bash grep"

# Include --disable-gtk here if you don't want things that depend on gtk3
EXTRA_OECONF += "--without-libmodbus --disable-check-runtime-deps --disable-tkinter --with-boost-python=boost_python312"

CONFIGUREOPTS:remove = "--disable-dependency-tracking"
CONFIGUREOPTS:remove = "--disable-silent-rules"
CONFIGUREOPTS:remove = "--with-libtool-sysroot"
EXTRA_OECONF:remove = "--disable-static"

# The __FILE__ or assert() macros both cause this warning to be spammed on debug builds
# Using DEBUG_PREFIX_MAP is another option to avoid this
INSANE_SKIP:${PN}-dbg += "buildpaths"

# LinuxCNC isn't very autotoolsy in that it doesn't use a Makefile.in
# Since the Makefile is always present, OE assumes a "make clean" 
# will always work (it won't without a ./configure first)
CLEANBROKEN = "1"

# Configure some extra packaged files for the base package
FILES:${PN}:append = " ${PYTHON_SITEPACKAGES_DIR}/* "

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

