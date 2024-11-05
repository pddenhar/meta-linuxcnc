LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://accelerate/license.txt;md5=615289fea9c74eaa77dc514e27560274 \
                    file://license.txt;md5=943332dbb441a49d1576fe75197d6cac"

SRC_URI = "git://git@github.com/mcfletch/pyopengl.git;protocol=ssh;branch=master;tag=release-3.1.8"

# Modify these as desired
PV = "3.1.8_git"

S = "${WORKDIR}/git"

inherit setuptools3

RDEPENDS:${PN} += "python3-core python3-ctypes python3-datetime python3-logging"

# WARNING: We were unable to map the following python package/module
# dependencies to the bitbake packages which include them:
#    Dialog
#    OpenGL.GLES3.OES
#    OpenGLContext
#    OpenGL_accelerate
#    OpenGL_accelerate.arraydatatype
#    OpenGL_accelerate.buffers_formathandler
#    OpenGL_accelerate.errorchecker
#    OpenGL_accelerate.latebind
#    OpenGL_accelerate.nones_formathandler
#    OpenGL_accelerate.numpy_formathandler
#    OpenGL_accelerate.vbo
#    OpenGL_accelerate.wrapper
#    Tkinter
#    genshi.template
#    lxml.etree
#    numpy
#    six.moves
