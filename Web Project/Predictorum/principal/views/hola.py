from django.shortcuts import render_to_response
from django.template.context import RequestContext
from principal.services.hola import prueba
def hola(request):
    a = prueba
    return render_to_response('hola.html',{'prueba':a},context_instance=RequestContext(request))