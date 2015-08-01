<div class="qzx-welcome">
    <h1>
        欢迎使用CMTS网络管理系统
    </h1>
    <button type="button" class="btn btn-primary btn-lg" id = "qzx-get-started" data-toggle="modal" data-target="#myModal">get started</button>
</div>
<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    cmts管理员登录
                </h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" role="form">
                    <div class="form-group">
                        <label for="firstname" class="col-sm-3 control-label">请输入用户</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="username"
                                   placeholder="请输入用户名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="lastname" class="col-sm-3 control-label">请输入密码</label>
                        <div class="col-sm-6">
                            <input type="password" class="form-control" id="password"
                                   placeholder="请输入密码">

                            <span class="qzx-font-warning hide" id = "qzx-warning-tip">用户名或密码错误</span>
                        </div>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" id = "qzx-cancel-btn">
                            取消
                        </button>
                        <button type="button" class="btn btn-primary" id = "qzx-login-btn">
                            登录
                        </button>
                    </div>
                </form>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->