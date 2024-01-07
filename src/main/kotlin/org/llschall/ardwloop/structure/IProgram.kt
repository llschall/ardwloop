package structure

import structure.data.SerialData
import structure.model.keyboard.KeyboardModel
import structure.model.keyboard.Keys

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
