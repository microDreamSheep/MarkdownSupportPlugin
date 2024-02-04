package indi.midreamsheep.app.tre.plugin.markdown.parser.paragraph.code.editor;

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

data class CodeStyle(
    var code: String,
    var range: IntRange,
    var color: Color,
 ) {
     fun isEmpty(): Boolean {
         return this == empty
     }

     companion object {

         fun build(value: String): AnnotatedString {
             val styles = findCode(value).sortedBy { it.range.first }
             return buildAnnotatedString {
                 var startIndex = 0
                 for (style in styles) {
                     if (style.isEmpty()) continue
                     if (style.range.first < startIndex) continue
                     append(value.substring(startIndex, style.range.first))
                     withStyle(SpanStyle(color = style.color)) { append(style.code) }
                     startIndex = style.range.last + 1
                 }
                 if (startIndex < value.length) {
                     append(value.substring(startIndex))
                 }
             }
         }

         private fun findCode(value: String): List<CodeStyle> {
             val codeStyles = mutableListOf<CodeStyle>()
             codeStyles.addAll(findReservedWord(value))
             //findFunction() 应该放在 findProperty() 的上方, 因为它们的区别在大多数情况下只是多了一个括号;
             //而根据 build 方法的逻辑 startIndex 是累加的, 因此它会跳过已经被高亮的代码
             codeStyles.addAll(findFunction(value))
             codeStyles.addAll(findProperty(value))
             codeStyles.addAll(findStringConstant(value))
             codeStyles.addAll(findOtherConstant(value))
             codeStyles.addAll(findAnnotation(value))
             return codeStyles
         }

         // 注释
         private fun findAnnotation(value: String): List<CodeStyle> {
             val regex = Regex("//.*|(?s)/\\*.*?\\*/")
             return regex.findAll(value)
                 .map { buildCodeStyle(it, Color(0XFF8C8C8C)) }
                 .toList()
         }

         // 保留字
         private fun findReservedWord(value: String): List<CodeStyle> {
             val regex =
                 Regex("\\b(break|case|catch|class|const|continue|debugger|default|delete|do|else|enum|export|extends|finally|for|function|if|implements|import|in|instanceof|interface|let|new|package|private|protected|public|return|static|super|switch|this|throw|try|typeof|var|void|while|with|yield|window)\b")
             return regex.findAll(value)
                 .map { buildCodeStyle(it, /*Color(0XFF0033B3)*/Color.Red) }
                 .toList()
         }

         // 方法(函数)
         private fun findFunction(value: String): List<CodeStyle> {
             val regex = Regex("(\\w+)\\s*?\\(")
             return regex.findAll(value)
                 .map {
                     //group[0] == all match
                     val first = it.groups[1] ?: return@map CodeStyle.empty
                     CodeStyle(
                         code = first.value,
                         range = first.range,
                         color = Color(0XFF7A7A43)
                     )
                 }
                 .toList()
         }

         // 属性(字段)
         private fun findProperty(value: String): List<CodeStyle> {
             val regex = Regex("\\.\\s*?(\\w+)")
             return regex.findAll(value)
                 .map {
                     //group[0] == all match
                     val first = it.groups[1] ?: return@map CodeStyle.empty
                     CodeStyle(
                         code = first.value,
                         range = first.range,
                         color = Color(0XFF871094)
                     )
                 }
                 .toList()
         }

         // 字符串字面量
         private fun findStringConstant(value: String): List<CodeStyle> {
             //用于匹配字符串字面量
             var regex = Regex("('.*?')|(\".*?\")")
             return regex.findAll(value)
                 .map { buildCodeStyle(it, Color(0XFF067D17)) }
                 .toList()
         }

         // 其他字面量
         private fun findOtherConstant(value: String): List<CodeStyle> {
             val regex = Regex("(?<=\\s|\\b)\\d+(\\.\\d+)?\\b|(?i)(\\btrue\\b|\\bfalse\\b|\\bnull\\b)")
             return regex.findAll(value)
                 .map { buildCodeStyle(it, Color(0XFF005CC5)) }
                 .toList()
         }

         private fun buildCodeStyle(result: MatchResult, color: Color): CodeStyle {
             return CodeStyle(
                 code = result.value,
                 range = result.range,
                 color = color
             )
         }

         val empty: CodeStyle
             get() = CodeStyle("", IntRange.EMPTY, Color.Transparent)
     }
 }
