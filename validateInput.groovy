/*
PingAccess Groovy script that inspects API requests and validates formatting
for incoming query parameters to check patterns (e.g. to block injection
attacks, or find pii).
*/
def ok_to_continue = true
def patternToTest = "[0-9A-Za-z]+"
exc?.log.info "API inspection policy"
def method = exc?.request?.method?.methodName
exc?.log.info "Request method : " + method
queryParams = exc?.request?.getQueryStringParams()
for (entry in queryParams) {
  exc?.log.info "Query parameter : " + entry.key
  for (value in entry.value) {
      exc?.log.info "Testing value : " + value
      def ishex = value.matches(patternToTest)
      if (!ishex) {
        ok_to_continue = false
      }
  }
}
if (ok_to_continue) {
  anything()
} else {
  not(anything())
}
