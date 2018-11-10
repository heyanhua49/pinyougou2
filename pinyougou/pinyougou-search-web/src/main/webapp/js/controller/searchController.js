app.controller("searchController", function ($scope, searchService) {

    //提交到后台的查询条件对象
    $scope.searchMap = {"keywords":"","category":"","brand":"","spec":{}, "price":""};

    //搜索
    $scope.search = function () {

        searchService.search($scope.searchMap).success(function (response) {
            $scope.resultMap = response;

        });

    };

    //添加查询过滤条件
    $scope.addSearchItem = function (key, value) {
        if ("category" == key || "brand" == key || "price" == key) {
            $scope.searchMap[key] = value;
        } else {
            //规格
            $scope.searchMap.spec[key] = value;
        }

        $scope.search();
    };

    //从搜索条件导航条中移除过滤条件
    $scope.removeSearchItem = function (key) {
        if ("category" == key || "brand" == key || "price" == key) {
            $scope.searchMap[key] = "";
        } else {
            //规格
            delete $scope.searchMap.spec[key];
        }

        $scope.search();
    };
});