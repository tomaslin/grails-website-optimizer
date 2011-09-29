package grails.plugin.gwo

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class WebsiteOptimizerTagLib {

	static namespace = 'gwo'

	def includes = { attrs ->

		def account = attrs.account ?: ConfigurationHolder.config.gwo.account
		if (!account)
		{
			throw new IllegalArgumentException("No Account specified in taglib or configuration file")
		}

		def experiment = attrs.experiment ?: ConfigurationHolder.config.gwo.defaultExperiment
		if (!experiment)
		{
			throw new IllegalArgumentException("No Experiment specified in taglib or configuration file")
		}

		if (attrs.control || attrs.multi )
		{
			out << """<!-- Google Website Optimizer Control Script -->
<script>
function utmx_section(){}function utmx(){}
(function(){var k='${experiment}',d=document,l=d.location,c=d.cookie;function f(n){
if(c){var i=c.indexOf(n+'=');if(i>-1){var j=c.indexOf(';',i);return escape(c.substring(i+n.
length+1,j<0?c.length:j))}}}var x=f('__utmx'),xx=f('__utmxx'),h=l.hash;
d.write('<sc'+'ript src="'+
'http'+(l.protocol=='https:'?'s://ssl':'://www')+'.google-analytics.com'
+'/siteopt.js?v=1&utmxkey='+k+'&utmx='+(x?x:'')+'&utmxx='+(xx?xx:'')+'&utmxtime='
+new Date().valueOf()+(h?'&utmxhash='+escape(h.substr(1)):'')+
'" type="text/javascript" charset="utf-8"></sc'+'ript>')})();
</script>"""

			if( attrs.control ) {
				out << """<script>utmx("url",'A/B');</script>"""
			}

			out << """
<!-- End of Google Website Optimizer Control Script -->
"""
		}

		out << """<!-- Google Website Optimizer Tracking Script -->
<script type="text/javascript">
  var _gaq = _gaq || [];
  _gaq.push(['gwo._setAccount', '${account}']);
  _gaq.push(['gwo._trackPageview', '/${experiment}/test']);
  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
</script>
<!-- End of Google Website Optimizer Tracking Script -->"""

	}

	def section = { attrs, body ->
		if (!attrs.name)
		{
			throw new IllegalArgumentException('Tag gwo:section is missing required attribute name')
		}

		out << """<script>utmx_section("${ attrs.name }")</script>"""
		out << body()
		out << "</noscript>"
	}

	def conversion = { attrs ->

		def account = attrs.account ?: ConfigurationHolder.config.gwo.account
		if (!account)
		{
			throw new IllegalArgumentException("No Account specified in taglib or configuration file")
		}

		def experiment = attrs.experiment ?: ConfigurationHolder.config.gwo.defaultExperiment
		if (!experiment)
		{
			throw new IllegalArgumentException("No Experiment specified in taglib or configuration file")
		}

		def time = 0

		if (attrs.time)
		{
			time = attrs.time as int
		}

		def functionName

		if( attrs.functionName )
		{
			functionName = attrs.functionName
		}

		out << """<!-- Google Website Optimizer Tracking Script -->
<script type="text/javascript">"""

		if( functionName ){
			out << """function ${functionName}(){"""
		}

out<< """
  var _gaq = _gaq || [];
  _gaq.push(['gwo._setAccount', '${account}']);"""

		if( time > 0 ){
			out << """
setTimeout(function() {"""
		}

out<<"""
  _gaq.push(['gwo._trackPageview', '/${experiment}/goal']);"""

		if( time > 0 ){
			out << """
}, ${ time });"""
		}

		if( functionName ){
			out << """}"""
		}

out<< """
  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
</script>
<!-- End of Google Website Optimizer Tracking Script -->"""

	}

}

