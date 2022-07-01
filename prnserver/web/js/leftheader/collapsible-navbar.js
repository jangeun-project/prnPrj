"use strict";

(function ($) {

  var collapseMaxHeight = function collapseMaxHeight() {
    return $(window).height() - $("#navbar-header").height();
  };

  var fixNavCollapseHeight = function fixNavCollapseHeight() {
    $("#navbar-right-collapse").css({
      maxHeight: collapseMaxHeight() + "px"
    });
  };

  $(window).on("resize", function () {
    return fixNavCollapseHeight();
  });
  $(function () {
    fixNavCollapseHeight();
  });
})(jQuery);
//# sourceMappingURL=collapsible-navbar.js.map
