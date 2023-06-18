(function(document, $, Coral) {
    "use strict";

    $(document).on("foundation-contentloaded", function(e) {
        $(".cmp-list__editor coral-checkbox.cq-dialog-checkbox-showhide", e.target).each(function(i, element) {
            var target = $(element).data("cq-dialog-checkbox-showhide-target");
            if (target) {
                Coral.commons.ready(element, function(component) {
                    showHideCheckbox(component, target,false,null);
                    $(component).on("change", function(event) {
                        showHideCheckbox(component, target,event.currentTarget.checked,event.currentTarget.value);
                    });
                });
            }
        });
        //showHideCheckbox($(".cq-dialog-checkbox-showhide", e.target));
    });

    function showHideCheckbox(component, target,checked,value) {
        var tabsContent=$(target).closest("coral-panelstack");
        var tabsList=tabsContent.siblings("coral-tablist");
        $(target).each(function(i, element) {
             var tabDiv=$(element).attr("data-istab");
             if(!checkUndefined(tabDiv)){
                showHideFields(element,checked,value);
             }
             if(checkUndefined(tabDiv)){
                showHideTabs(element,checked,value);
             }
    })
}
    function showHideFields(element,checked,value){
        var continerShow=$(element).attr("data-showhidecheckboxvalue");
        if(value==continerShow && checked){
           $(element).removeClass("hide");
        }if(value==continerShow && !checked){
            $(element).addClass("hide");
        }
    }

    function showHideTabs(target,checked,value){
        console.log("========> ",checked,value);
        //initialHideTabs(target,checked,value);
        var tabsContent=$(target).closest("coral-panelstack");
        var tabsList=tabsContent.siblings("coral-tablist");
        tabsContent.children().each(function(i, element) {
             var tabShowValue=$(element).find("div").attr("data-showhidecheckboxvalue");
            if(checkUndefined(tabShowValue)){
             if(value==tabShowValue && checked){
               var tabIDShow= $(element).attr("aria-labelledby");
               $(element).attr("selected","");  
               $(tabsList).children("[id='" + tabIDShow + "']").removeClass("hide");
               $(element).find("div").filter("[data-showhidecheckboxvalue='" + value + "']").removeClass("hide");
             }else if((value==tabShowValue || value==null) && !checked){
                var tabIDHide= $(element).attr("aria-labelledby");
                $(tabsList).children("[id='" + tabIDHide + "']").addClass("hide");
             }
            }
        });
    }

    function initialHideTabs(target,checked,value){
        console.log("====initialHideTabs====> ",checked,value);
        var tabsContent=$(target).closest("coral-panelstack");
        var tabsList=tabsContent.siblings("coral-tablist");
        tabsContent.children().each(function(i, element) {
            var tabDiv=$(element).attr("data-istab");
            if(value==null && !checkUndefined(tabDiv)){
               

               var tabIDHide= $(element).attr("aria-labelledby");
               $(tabsList).children("[id='" + tabIDHide + "']").addClass("hide");
               $(element).addClass("hide");
             
                //var tabIDHide= $(element).attr("aria-labelledby");
                //$(tabsList).children("[id='" + tabIDHide + "']").addClass("hide");
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