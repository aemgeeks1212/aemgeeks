(function(document, $, Coral) {
    "use strict";

    $(document).on("foundation-contentloaded", function(e) {
        $(".cmp-list__editor div.cq-dialog-radio-showhide", e.target).each(function(i, element) {
            var target = $(element).data("cq-dialog-radio-showhide-target");
            if (target) {
                Coral.commons.ready(element, function(component) {
                    showHideRadio(component, target,null);
                    $(component).on("change", function(event) {
                        showHideRadio(component, target,event.target.value);
                    });
                });
            }
        });
        showHideRadio($(".cq-dialog-radio-showhide", e.target));
    });

    function showHideRadio(component, target,value) {
        $(target).each(function(i, element) {
             var tabDiv=$(element).attr("data-istab");
             if(!checkUndefined(tabDiv)){
                showHideFields(element,value);
             }
             if(checkUndefined(tabDiv)){
                showHideTabs(element,value);
             }
    });
}
    function showHideFields(element,value){
        var continerShow=$(element).attr("data-showhideradiovalue");
        if(value==continerShow){
           $(element).removeClass("hide");
        }else{
           $(element).addClass("hide");
        }
    }

    function showHideTabs(target,value){
        var tabsContent=$(target).closest("coral-panelstack");
        var tabsList=tabsContent.siblings("coral-tablist");
        tabsContent.children().each(function(i, element) {
             var tabShowValue=$(element).find("div").attr("data-showhideradiovalue");
            if(checkUndefined(tabShowValue)){
             if(value==tabShowValue){
               var tabIDShow= $(element).attr("aria-labelledby");
               $(element).attr("selected","");  
               $(tabsList).children("[id='" + tabIDShow + "']").removeClass("hide");
               $(element).find("div").filter("[data-showhideradiovalue='" + value + "']").removeClass("hide");
             }else if(value!=tabShowValue){
                var tabIDHide= $(element).attr("aria-labelledby");
                $(tabsList).children("[id='" + tabIDHide + "']").addClass("hide");
             }
            }
        });
    }

    function checkUndefined(target){
        if(typeof target === "undefined"){
            return false;
        }
        return true;
    }

})(document, Granite.$, Coral);