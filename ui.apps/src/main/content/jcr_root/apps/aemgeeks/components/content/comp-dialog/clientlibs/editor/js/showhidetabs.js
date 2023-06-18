(function(document, $, Coral) {
    "use strict";

    $(document).on("foundation-contentloaded", function(e) {
        $(".cmp-list__editor coral-select.cq-dialog-dropdown-showhide", e.target).each(function(i, element) {
            var target = $(element).data("cq-dialog-dropdown-showhide-target");
            if (target) {
                Coral.commons.ready(element, function(component) {
                    showHideTab(component, target);
                    component.on("change", function() {
                        showHideTab(component, target);
                    });
                });
            }
        });
        showHideTab($(".cq-dialog-dropdown-showhide", e.target));
    });

    function showHideTab(component, target) {
        var value = component.value;
         console.log("=======VALUE=======>",value);
        $(target).not(".hide").addClass("hide");
        //console.log("=======TARGET=======>",$(target).html());

        var tabsContent=$(target).closest("coral-panelstack");
        var tabsList=tabsContent.siblings("coral-tablist");
        tabsContent.children().each(function(i, element) {
             var tabShowValue=$(element).find("div").attr("data-showhidetargetvalue");

            if(checkUndefined(tabShowValue)){
                console.log("=======tabShowValue=======>",tabShowValue);
             if(value==tabShowValue){
             console.log("======TAB=CONTENT=====>",$(element).find("div").attr("data-showhidetargetvalue"));
               var tabIDShow= $(element).attr("aria-labelledby");
               //$(element).attr("aria-hidden","false");
               $(element).attr("selected","");  
               //$(element).addClass("is-selected");
               $(tabsList).children("[id='" + tabIDShow + "']").removeClass("hide");
               console.log("======TAB=SELECTED =====>",tabIDShow);

             }else if(value!=tabShowValue){
                var tabIDHide= $(element).attr("aria-labelledby");
                $(tabsList).children("[id='" + tabIDHide + "']").addClass("hide")
                console.log("======TAB=HIDDEN =====>",tabIDHide);

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