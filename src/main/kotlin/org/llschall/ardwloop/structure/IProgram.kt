package org.llschall.ardwloop.structure

import org.llschall.ardwloop.structure.data.SerialData
import org.llschall.ardwloop.structure.model.keyboard.KeyboardModel
import org.llschall.ardwloop.structure.model.keyboard.Keys

interface IProgram {

    fun setup(s: SerialData?): SerialData?

    fun loop(keyboardMdl: KeyboardModel?, r: SerialData?): SerialData?

    fun post(p: SerialData?)

    val commands: Map<Keys?, String?>?
        get() = HashMap()

    val name: String?

    val id: Char

    val rc: Int

    val sc: Int
}