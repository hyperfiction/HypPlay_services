#ifndef STATIC_LINK
#define IMPLEMENT_API
#endif

#if defined(HX_WINDOWS) || defined(HX_MACOS) || defined(HX_LINUX)
#define NEKO_COMPATIBLE
#endif


#include <hx/CFFI.h>
#include "Utils.h"


using namespace hyp_playservices;



static value hyp_playservices_sample_method (value inputValue) {
	
	int returnValue = SampleMethod(val_int(inputValue));
	return alloc_int(returnValue);
	
}
DEFINE_PRIM (hyp_playservices_sample_method, 1);



extern "C" void hyp_playservices_main () {
	
	val_int(0); // Fix Neko init
	
}
DEFINE_ENTRY_POINT (hyp_playservices_main);



extern "C" int hyp_playservices_register_prims () { return 0; }