package men.brakh.bsuirstudent

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext

import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component


@Component
class SpringContext : ApplicationContextAware {
    override fun setApplicationContext(context: ApplicationContext) {
        Companion.context = context
    }

    companion object {
        private lateinit var context: ApplicationContext

        fun <T : Any?> getBean(beanClass: Class<T>): T {
            return context.getBean(beanClass)
        }
    }
}