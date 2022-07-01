"use strict";

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

(function ($) {
  var CollapsibleSubMenu = function () {
    function CollapsibleSubMenu(settings) {
      _classCallCheck(this, CollapsibleSubMenu);

      this.subMenu = $(settings.subMenu);
      this.collapseToggle = $(settings.collapseToggle);
      this.collapsedMenu = $(settings.collapsedMenu);
      this.menuItems = settings.menuItems;
    }

    _createClass(CollapsibleSubMenu, [{
      key: "init",
      value: function init() {
        var _this = this;

        $(window).resize(function () {
          return _this.refresh();
        });
        this.refresh();
      }
    }, {
      key: "grow",
      value: function grow() {
        var lastItem = void 0;
        while (!this.hasScrollBar() && this.collapsedMenuItems().size() > 0) {
          lastItem = this.collapsedMenuItems().first();
          lastItem.insertBefore(this.collapseToggle);
        }

        if (this.hasScrollBar()) {
          lastItem.prependTo(this.collapsedMenu);
          this.collapseToggle.show();
        } else {
          this.collapseToggle.hide();
        }
      }
    }, {
      key: "shrink",
      value: function shrink() {
        var showToggle = false;
        while (this.hasScrollBar()) {
          var subMenuItems = this.subMenuItems();
          var subMenuCount = subMenuItems.size();
          if (subMenuCount <= 1) {
            break;
          }
          var item = $(subMenuItems[subMenuCount - 1]);
          item.prependTo(this.collapsedMenu);
          showToggle = true;
        }
        this.collapseToggle.toggle(showToggle);
      }
    }, {
      key: "refresh",
      value: function refresh() {
        return this.hasScrollBar() ? this.shrink() : this.grow();
      }
    }, {
      key: "subMenuItems",
      value: function subMenuItems() {
        return this.subMenu.children(this.menuItems);
      }
    }, {
      key: "collapsedMenuItems",
      value: function collapsedMenuItems() {
        return this.collapsedMenu.children(this.menuItems);
      }
    }, {
      key: "hasScrollBar",
      value: function hasScrollBar() {
        var item = this.subMenu.parent()[0];
        return item && item.offsetWidth < item.scrollWidth;
      }
    }]);

    return CollapsibleSubMenu;
  }();

  window.CollapsibleSubMenu = CollapsibleSubMenu;
})(jQuery);
//# sourceMappingURL=collapsible-submenu.js.map
