package men.brakh.newsparser.model.parser.site


import com.overzealous.remark.Options
import com.overzealous.remark.Remark
import men.brakh.newsparser.model.parser.Parser
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


abstract class SiteParser : Parser {

    protected fun String.toMd(): String {
        val remark = Remark(Options.markdownExtra())

        return remark.convertFragment(this)
    }

    protected fun getDom(url: String): Document {
        return Jsoup.connect(url)
                .get()
    }
}