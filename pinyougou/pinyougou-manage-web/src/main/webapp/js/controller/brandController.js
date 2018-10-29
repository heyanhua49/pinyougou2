//定义处理器
app.controller("brandController", function ($scope, $http, brandService) {

    $scope.findAll = function () {

        brandService.findAll().success(function (response) {
            //将返回的品牌列表设置到一个变量中
            $scope.list = response;
        }).error(function () {
            alert("加载数据失败！");
        });

    };


    // 初始化分页参数
    $scope.paginationConf = {
        currentPage: 1,// 当前页号
        totalItems: 10,// 总记录数
        itemsPerPage: 10,// 页大小
        perPageOptions: [10, 20, 30, 40, 50],// 可选择的每页大小
        onChange: function () {// 当上述的参数发生变化了后触发；渲染分页组件完之后也会触发
            $scope.reloadList();
        }
    };

    $scope.reloadList = function () {
        //$scope.findPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
        $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    };

    //根据分页查询
    $scope.findPage = function (page, rows) {
        brandService.findPage(page, rows)
            .success(function (pageResult) {
                //更新list
                $scope.list = pageResult.rows;
                //更新分页插件中的总记录数
                $scope.paginationConf.totalItems = pageResult.total;
            });
    };

    //保存
    $scope.save = function () {

        var obj;
        //如果是修改的话；则修改为update
        if($scope.entity.id != null){
            obj = brandService.update($scope.entity);
        } else {
            //新增
            obj = brandService.add($scope.entity);
        }

        obj.success(function (response) {
            if(response.success){
                //操作成功；则刷新列表
                $scope.reloadList();
                //$scope.entity={};
            } else {
                alert(response.message);
            }
        });
    };

    //根据主键查询
    $scope.findOne = function (id) {
        brandService.findOne(id).success(function (response) {
            $scope.entity = response;
        });
    };

    //当前选中了的那些id数组
    $scope.selectedIds = [];

    //复选框点击事件
    $scope.updateSelection = function ($event, id) {

        if($event.target.checked){
            //当选中某个品牌的时候，应该将该品牌的id加入数组
            $scope.selectedIds.push(id);
        } else {
            //如果反选某个已经选中的品牌复选框的时候，应该将该品牌id从数组删除
            //先查询id在数组中的索引号
            var index = $scope.selectedIds.indexOf(id);
            //使用splic方法删除对应索引号的元素，删除个数为1
            $scope.selectedIds.splice(index, 1);
        }
    };

    //删除
    $scope.delete = function () {
        //判断是否选择了
        if($scope.selectedIds.length == 0){
            alert("请先选择要删除的记录");
            return;
        }

        //如果点击了 确定 则返回true；否则false
        if(confirm("确定要删除选中的那些记录吗？")){
            brandService.delete($scope.selectedIds).success(function (response) {
                if(response.success){
                    $scope.reloadList();
                    //将已选择的id清空
                    $scope.selectedIds = [];
                } else {
                    alert(response.message);
                }
            });
        }

    };


    $scope.searchEntity = {};

    //查询
    $scope.search = function (page, rows) {
        brandService.search(page, rows, $scope.searchEntity).success(function (response) {
            $scope.paginationConf.totalItems = response.total;
            $scope.list = response.rows;
        });

    };

});
