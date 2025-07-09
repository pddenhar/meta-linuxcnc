LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://github.com/LinuxCNC/linuxcnc.git;protocol=https;branch=master \
           file://0002-configure.ac-Add-BUILD_TKINTER-flag.patch; \
           file://0001-Un-ruin-the-ridiculous-shebang-substitution-for-pyth.patch; \
           file://0001-Makefile-Add-LDFLAGS-to-panelui-and-module_helper-li.patch; \
           file://use-standard-sitepy.patch; \
           file://0005-Remove-compile-time-paths-from-.in-files.patch; \
           file://0006-Do-not-use-hardcoded-shebang-for-mesambccc.py.patch; \
           "

# python3targetconfig: configuration for the target machine is accessible (such as correct installation directories)
# autotools-brokensep: autotools build process (without support for building outside of ${S})
# pkgconfig: standard way to get header and library information by using pkg-config
inherit autotools-brokensep pkgconfig python3native

# Modify these as desired
PV = "2.10.0~pre0+git"
SRCREV = "90f51a6ba0e578fe3d559005981d06289c2ffb8c"

S = "${WORKDIR}/git"
# LinuxCNC build separation is broken, so we need to build in src
B = "${S}/src"
AUTOTOOLS_SCRIPT_PATH = "${B}"

# We need to replace autotools_preconfigure with our own, because it deletes ${B} otherwise
autotools_preconfigure() {
	if [ -n "${CONFIGURESTAMPFILE}" -a -e "${CONFIGURESTAMPFILE}" ]; then
		if [ "`cat ${CONFIGURESTAMPFILE}`" != "${BB_TASKHASH}" ]; then
            find ${S} -ignore_readdir_race -name \*.la -delete
        fi
	fi
}


DEPENDS += "libtirpc libusb1 glib-2.0 gtk+3 python3-yapps2-native intltool-native \
boost boost-native python3 python3-native tcl8 tcl8-native tk8 xinerama readline libglu libxmu \
asciidoc-native groff-native" 
# These ones were missing from configure.ac checks

# tk rprovides wish, tk-lib provides libtk8.6.so
# libgl-mesa rprovides libgl
RDEPENDS:${PN} += "tcl8 tk8 tk8-lib python3-core bash grep bwidget libgl python3-pygobject python3-pyopengl libglu"

# Include --disable-gtk here if you don't want things that depend on gtk3
# --with-boost is critical if your host system has boost installed on it. Otherwise ax_boost_base finds that
EXTRA_OECONF += "--without-libmodbus --disable-check-runtime-deps --disable-tkinter --with-boost-python=boost_python313 --with-boost=${RECIPE_SYSROOT}/usr"

CONFIGUREOPTS:remove = "--disable-dependency-tracking"
CONFIGUREOPTS:remove = "--disable-silent-rules"
CONFIGUREOPTS:remove = "--with-libtool-sysroot"
EXTRA_OECONF:remove = "--disable-static"

# Workaround for outdated use of the "CONST" macro in emcsh.cc
#CFLAGS:append = " -DCONST=const"

# C compiler flags
CFLAGS:append = " -fpermissive -Wno-implicit-function-declaration -Wall -Wextra"

# C++ compiler flags
CXXFLAGS:append = " -fpermissive -Wall -Wextra"


# The __FILE__ or assert() macros both cause this warning to be spammed on debug builds
# Using DEBUG_PREFIX_MAP is another option to avoid this
INSANE_SKIP:${PN}-dbg += "buildpaths"

# LinuxCNC isn't very autotoolsy in that it doesn't use a Makefile.in
# Since the Makefile is always present, OE assumes a "make clean" 
# will always work (it won't without a ./configure first)
CLEANBROKEN = "1"

# Configure some extra packaged files for the base package
FILES:${PN}:append = " ${PYTHON_SITEPACKAGES_DIR}/* \
                       ${libdir}/tcltk/* \
                       ${datadir}/axis/* \ 
                       ${datadir}/gtksourceview-4/* \ 
                       ${datadir}/gscreen/* \ 
                       ${datadir}/glade/* \ 
                       ${datadir}/qtvcp/* \ 
                       ${datadir}/gmoccapy/* \
                       "


