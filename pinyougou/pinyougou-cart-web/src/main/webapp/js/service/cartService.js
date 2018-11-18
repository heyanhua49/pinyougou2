app.service("cartService", function ($http) {

    this.getUsername = function () {
        return $http.get("cart/getUsername.do");

    };

    this.findCartList = function () {
        return $http.get("cart/findCartList.do");

    }
});