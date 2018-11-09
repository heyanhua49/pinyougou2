app.controller("searchController", function ($scope, searchService) {

    //提交到后台的查询条件对象
    $scope.searchMap = {"keywords":""};

    //搜索
    $scope.search = function () {

        searchService.search($scope.searchMap).success(function (response) {
            $scope.resultMap = response;

        });

    };
});