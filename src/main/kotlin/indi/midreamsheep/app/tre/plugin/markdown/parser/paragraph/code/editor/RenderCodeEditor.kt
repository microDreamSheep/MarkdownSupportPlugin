package indi.midreamsheep.app.tre.plugin.markdown.parser.paragraph.code.editor

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.em

@Composable
fun CodeEditor(
    modifier: Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.bodySmall.copy(lineHeight = 1.3.em),
        modifier = modifier,
        visualTransformation = { text ->
            TransformedText(
                text = CodeStyle.build(text.text),
                offsetMapping = OffsetMapping.Identity
            )
        }
    ) {
        if (value.text.isEmpty()) {
            Text(
                text = "请在这里书写代码片段",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.typography.bodySmall.color.copy(0.5f)
                )
            )
        }
        it.invoke()
    }
}


