package indi.midreamsheep.app.tre.plugin.markdown.parser.paragraph.code

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import indi.midreamsheep.app.tre.context.di.inject.mapdi.annotation.MapInjector
import indi.midreamsheep.app.tre.context.editor.TREEditorContext
import indi.midreamsheep.app.tre.model.editor.parser.ParagraphParser
import indi.midreamsheep.app.tre.plugin.markdown.parser.paragraph.code.editor.CodeEditorLine
import live.midreamsheep.frame.sioc.di.annotation.basic.comment.Comment

@MapInjector(target = "paragraph",key="`")
@Comment
class CodeParagraphParser: ParagraphParser {
    override fun formatCheck(text: String): Boolean {
        if (text.length<3) return false
        if (text[0]!='`') return false
        if (text[1]!='`') return false
        if (text[2]!='`') return false
        return true
    }

    override fun getComposable(
        text: String,
        recall: () -> Unit,
        stateList: indi.midreamsheep.app.tre.model.editor.manager.TREStateManager,
        state: indi.midreamsheep.app.tre.model.editor.line.core.CoreTRELine
    ):@Composable () -> Unit {
        val codeLine =   CodeEditorLine(state.wrapper)
        state.wrapper.line =  codeLine
        text.subSequence(3,text.length-3).toString().let {
            codeLine.type = it
        }
        return {
            Text("awdawdawdaw")
        }
    }

    override fun analyse(texts: List<String>, lineNumber: Int, state: indi.midreamsheep.app.tre.model.editor.manager.TREStateManager): Int {
        return lineNumber+1
    }
}