class WebsiteOptimizerGrailsPlugin {
    // the plugin version
    def version = "0.2"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.2.0 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp",
			"src/docs"
    ]

    def author = "Tomas Lin"
    def authorEmail = "tomaslin@gmail.com"
    def title = "Google Website Optimizer"
    def description = '''\\
This plugin provides tags to easily integrate google website optimizer experiments into your grails application
'''

    // URL to the plugin's documentation
    def documentation = "http://tomaslin.github.com/grails-website-optimizer/"

    def doWithWebDescriptor = { xml ->
    }

    def doWithSpring = {
    }

    def doWithDynamicMethods = { ctx ->
    }

    def doWithApplicationContext = { applicationContext ->
    }

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }

}
