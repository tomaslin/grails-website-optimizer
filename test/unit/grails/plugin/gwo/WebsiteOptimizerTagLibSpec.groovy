package grails.plugin.gwo

import grails.plugin.spock.TagLibSpec

class WebsiteOptimizerTagLibSpec extends TagLibSpec {

	def setup() {
		String.metaClass.noWhiteSpace = { return this.toString().replaceAll("\\s", "") }
	}

	def "include tag for control is embedded correctly"() {

		setup:
		mockConfig ''

		expect:
		includes(account: 'UA-25983867-2', experiment: '3228514949', control: true) == '''<!-- Google Website Optimizer Control Script -->
<script>
function utmx_section(){}function utmx(){}
(function(){var k='3228514949',d=document,l=d.location,c=d.cookie;function f(n){
if(c){var i=c.indexOf(n+'=');if(i>-1){var j=c.indexOf(';',i);return escape(c.substring(i+n.
length+1,j<0?c.length:j))}}}var x=f('__utmx'),xx=f('__utmxx'),h=l.hash;
d.write('<sc'+'ript src="'+
'http'+(l.protocol=='https:'?'s://ssl':'://www')+'.google-analytics.com'
+'/siteopt.js?v=1&utmxkey='+k+'&utmx='+(x?x:'')+'&utmxx='+(xx?xx:'')+'&utmxtime='
+new Date().valueOf()+(h?'&utmxhash='+escape(h.substr(1)):'')+
'" type="text/javascript" charset="utf-8"></sc'+'ript>')})();
</script><script>utmx("url",'A/B');</script>
<!-- End of Google Website Optimizer Control Script -->
<!-- Google Website Optimizer Tracking Script -->
<script type="text/javascript">
  var _gaq = _gaq || [];
  _gaq.push(['gwo._setAccount', 'UA-25983867-2']);
  _gaq.push(['gwo._trackPageview', '/3228514949/test']);
  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
</script>
<!-- End of Google Website Optimizer Tracking Script -->'''

	}

	def "include tag for variation is embedded correctly"() {

		setup:
		mockConfig ''

		expect:
		includes(account: 'UA-25983867-2', experiment: '3228514949', variation: true) == '''<!-- Google Website Optimizer Tracking Script -->
<script type="text/javascript">
  var _gaq = _gaq || [];
  _gaq.push(['gwo._setAccount', 'UA-25983867-2']);
  _gaq.push(['gwo._trackPageview', '/3228514949/test']);
  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
</script>
<!-- End of Google Website Optimizer Tracking Script -->'''

	}

	def "conversion tag is set correctly for AB tests"() {

		setup:
		mockConfig ''

		expect: 'tag must be identical to that from the conversion tag from gwo'
		conversion(account: 'UA-25983867-2', experiment: '3228514949') == '''<!-- Google Website Optimizer Tracking Script -->
<script type="text/javascript">
  var _gaq = _gaq || [];
  _gaq.push(['gwo._setAccount', 'UA-25983867-2']);
  _gaq.push(['gwo._trackPageview', '/3228514949/goal']);
  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
</script>
<!-- End of Google Website Optimizer Tracking Script -->'''

	}

	def "conversion tag for Multivariate test is correctly generated"() {

		setup:
		mockConfig ''

		expect: 'tag must be identical to that from the conversion tag from gwo multivariate test'
		conversion(account: 'UA-25983867-2', experiment: '1361611023') == '''<!-- Google Website Optimizer Tracking Script -->
<script type="text/javascript">
  var _gaq = _gaq || [];
  _gaq.push(['gwo._setAccount', 'UA-25983867-2']);
  _gaq.push(['gwo._trackPageview', '/1361611023/goal']);
  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
</script>
<!-- End of Google Website Optimizer Tracking Script -->'''

	}

	def "include tag for multivariate is generated correctly"() {
		setup:
		mockConfig ''

		expect:
		includes(account: 'UA-25983867-2', experiment: '1361611023', multi: true) == '''<!-- Google Website Optimizer Control Script -->
<script>
function utmx_section(){}function utmx(){}
(function(){var k='1361611023',d=document,l=d.location,c=d.cookie;function f(n){
if(c){var i=c.indexOf(n+'=');if(i>-1){var j=c.indexOf(';',i);return escape(c.substring(i+n.
length+1,j<0?c.length:j))}}}var x=f('__utmx'),xx=f('__utmxx'),h=l.hash;
d.write('<sc'+'ript src="'+
'http'+(l.protocol=='https:'?'s://ssl':'://www')+'.google-analytics.com'
+'/siteopt.js?v=1&utmxkey='+k+'&utmx='+(x?x:'')+'&utmxx='+(xx?xx:'')+'&utmxtime='
+new Date().valueOf()+(h?'&utmxhash='+escape(h.substr(1)):'')+
'" type="text/javascript" charset="utf-8"></sc'+'ript>')})();
</script>
<!-- End of Google Website Optimizer Control Script -->
<!-- Google Website Optimizer Tracking Script -->
<script type="text/javascript">
  var _gaq = _gaq || [];
  _gaq.push(['gwo._setAccount', 'UA-25983867-2']);
  _gaq.push(['gwo._trackPageview', '/1361611023/test']);
  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
</script>
<!-- End of Google Website Optimizer Tracking Script -->'''
	}

	def "Exception is thrown for tags that require experiment and account"() {

		setup:
		mockConfig ''

		when:
		"${tagMethod}"(experiment: '42424224')

		then:
		def e = thrown(IllegalArgumentException)
		e.message == 'No Account specified in taglib or configuration file'

		when:
		"${tagMethod}"(account: 'UA-0000333')

		then:
		e = thrown(IllegalArgumentException)
		e.message == 'No Experiment specified in taglib or configuration file'

		where:
		testCase | tagMethod
		0        | 'includes'
		1        | 'conversion'

	}

	def "can set a timeout on conversions"(){

		expect:
			conversion(account: 'UA-0000001',experiment: '9999999', time:4000).contains '''
setTimeout(function() {
  _gaq.push(['gwo._trackPageview', '/9999999/goal']);
}, 4000);
'''

	}

	def "can set a functionName for javaScript"(){
		when:
			def conversionString = conversion(account: 'UA-0000001',experiment: '9999999', functionName: 'trackConversion')
		then:
			conversionString.contains 'function trackConversion(){'
	}

	def "can read account details from config correctly"() {
		setup:
		mockConfig '''gwo.account = 'UA-9994044' '''
		expect:
		conversion(experiment: '9999999').contains '''['gwo._setAccount', 'UA-9994044']'''
		includes(experiment: '9999999', multi: true).contains '''['gwo._setAccount', 'UA-9994044']'''
	}

	def "can read experiment details from config correctly"() {
		setup:
		mockConfig '''gwo.defaultExperiment = '000000000' '''
		expect:
		conversion(account: 'UA-49494').contains '''_gaq.push(['gwo._trackPageview', '/000000000'''
		includes(account: 'UA-49494', multi: true).contains '''_gaq.push(['gwo._trackPageview', '/000000000'''
	}

	def "section tags are generated correctly"() {

		when:
			def sectionText = section(name: 'testSection'){ 'this is the body' }

		then:
			sectionText.startsWith '<script>utmx_section("testSection")</script>'
			sectionText.endsWith '</noscript>'

	}

	def "section throws an exception if name is not specified"() {

		when:
		section(test: 'test') { 'oops, no body '}

		then:
		def e = thrown(IllegalArgumentException)
		e.message == 'Tag gwo:section is missing required attribute name'

	}

	def "Url replace throws exception if values are not set"() {

		when:
		replaceUrl()

		then:
		def e = thrown(IllegalArgumentException)
		e.message == 'Tag gwo:replaceUrl is missing required attribute values'

	}


	def "URL values are replaced correctly by replace url"() {

		setup:
		mockConfig ''

		expect:
		replaceUrl( values: [ 'one' : 'two' ] ) == '''<!-- utmx section name="page-url" -->
<script>
var b = utmx('variation_content', 'page-url');
function filter(v) {
  var u = v[0].contents;
  if (b && u.substr(0,7) == 'http://' && b.substr(0, 7) != 'http://') {
		u = u.substr(7);
  }

  var l = document.location.href;
  u = u.replace('one', 'two');

  return u;
}
utmx('url', 'page-url', 0, filter);
</script>
'''

	}

	def 'url replace handles multiple replacement'(){
		setup:
			mockConfig ''

		when:
			def replaceScript = replaceUrl( values: [ '1' : 'one', '2': 'two', '3': 'three' ] )

		then:
			replaceScript.contains( '''u = u.replace('1', 'one');''' )
			replaceScript.contains( '''u = u.replace('2', 'two');''' )
			replaceScript.contains( '''u = u.replace('3', 'three');''' )
	}


}
