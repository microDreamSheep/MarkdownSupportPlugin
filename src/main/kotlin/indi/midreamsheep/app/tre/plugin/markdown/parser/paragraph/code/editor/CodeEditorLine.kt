package indi.midreamsheep.app.tre.plugin.markdown.parser.paragraph.code.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.line.TRELineInter
import indi.midreamsheep.app.tre.model.editor.line.TRELineState

class CodeEditorLine(private var wrapper: TRELineState) : TRELineInter {

    var content  = mutableStateOf(TextFieldValue(""))
    private var selection = 0
    var isFocus = mutableStateOf(false)
    private var focusRequester: FocusRequester = FocusRequester()
    var type = ""

    override fun focusFormStart() {
         focus()
        content.value = content.value.copy(selection =  TextRange(0))
        selection = 0
    }

    override fun focusFromLast() {
        focus()
        content.value = content.value.copy(selection =  TextRange(content.value.text.length))
        selection = content.value.text.length
    }

    override fun focus() {
        isFocus.value = true
        wrapper.markdownLineInter.setCurrentMarkdownLineState(wrapper)
        content.value = content.value.copy(selection = TextRange(selection))
        focusRequester.requestFocus()
    }

    override fun getComposable(context: TREEditorContext):@Composable () -> Unit {
        return {
            Column {
                //调用
                CodeEditor(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .background(Color.Gray)
                        .focusRequester(focusRequester),
                    value = content.value,
                    onValueChange = {
                        //只需要在每次文本发生改变时处理一下就行了
                        content.value = it
                    },

                )
            }
        }
    }

    override fun getContent(): String {
        return "```${this.type}\n${content.value.text}\n```"
    }

    override fun releaseFocus() {
        focusRequester.freeFocus()
    }

}