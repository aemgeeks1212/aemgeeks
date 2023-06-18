(function(document, $, Coral) {
    "use strict";

    $(document).on("foundation-contentloaded", function(e) {
        $(".cmp-list__editor coral-select.cq-dialog-dropdown-showhide", e.target).each(function(i, element) {
            var target = $(element).data("cq-dialog-dropdown-showhide-target");
            if (target) {
                Coral.commons.ready(element, function(component) {
                    showHide(component, target);
                    component.on("change", function() {
                        showHide(component, target);
                    });
                });
            }
        });
        showHide($(".cq-dialog-dropdown-showhide", e.target));
    });

    function showHide(component, target) {
        var value = component.value;
        $(target).not(".hide").addClass("hide");
        $(target).filter("[data-showhidetargetvalue='" + value + "']").removeClass("hide");
    }

})(document, Granite.$, Coral);