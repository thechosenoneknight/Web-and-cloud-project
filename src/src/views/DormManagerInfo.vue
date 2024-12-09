<template>
  <div>
    <el-breadcrumb separator-icon="ArrowRight" style="margin: 16px">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>User Management</el-breadcrumb-item>
      <el-breadcrumb-item>Dormitory management information</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card style="margin: 15px; min-height: calc(100vh - 111px)">
      <div>
        <!--    功能区-->
        <div style="margin: 10px 0">
          <!--    搜索区-->
          <div style="margin: 10px 0">
            <el-input v-model="search" clearable placeholder="Please enter your name" prefix-icon="Search" style="width: 20%"/>
            <el-button icon="Search" style="margin-left: 5px" type="primary" @click="load"></el-button>
            <el-button icon="refresh-left" style="margin-left: 10px" type="default" @click="reset"></el-button>
            <div style="float: right">
              <el-tooltip content="add" placement="top">
                <el-button icon="plus" style="width: 50px" type="primary" @click="add"></el-button>
              </el-tooltip>
            </div>
          </div>
        </div>
        <!--    表格-->
        <el-table v-loading="loading" :data="tableData" border max-height="705" style="width: 100%">
          <el-table-column label="#" type="index"/>
          <el-table-column label="username" prop="username" sortable/>
          <el-table-column label="name" prop="name"/>
          <el-table-column
              :filter-method="filterTag"
              :filters="[
              { text: 'boy', value: '男' },
              { text: 'gril', value: '女' },
            ]"
              filter-placement="bottom-end"
              label="gender"
              prop="gender"
          />
          <el-table-column label="age" prop="age" sortable/>
          <el-table-column label="phoneNum" prop="phoneNum"/>
          <el-table-column label="email" prop="email"/>
          <el-table-column label="dormBuildId" prop="dormBuildId" sortable/>
          <!--      操作栏-->
          <el-table-column label="operation" width="130px">
            <template #default="scope">
              <el-button icon="Edit" type="primary" @click="handleEdit(scope.row)"></el-button>
              <el-popconfirm title="confirm delete？" @confirm="handleDelete(scope.row.username)">
                <template #reference>
                  <el-button icon="Delete" type="danger"></el-button>
                </template>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>
        <!--分页-->
        <div style="margin: 10px 0">
          <el-pagination
              v-model:currentPage="currentPage"
              :page-size="pageSize"
              :page-sizes="[10, 20]"
              :total="total"
              layout="total, sizes, prev, pager, next, jumper"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
          >
          </el-pagination>
        </div>
        <div>
          <!--      弹窗-->
          <el-dialog v-model="dialogVisible" title="operation" width="30%" @close="cancel">
            <el-form ref="form" :model="form" :rules="rules" label-width="120px">
              <el-form-item label="username" prop="username">
                <el-input v-model="form.username" :disabled="judgeAddOrEdit" style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="password" prop="password">
                <el-input v-model="form.password" :disabled="disabled" :show-password="showpassword"
                          style="width: 80%"></el-input>
                <el-tooltip content="Change Password" placement="right">
                  <el-icon :style="editDisplay" size="large" style="margin-left: 5px; cursor: pointer"
                           @click="EditPass">
                    <edit/>
                  </el-icon>
                </el-tooltip>
              </el-form-item>
              <el-form-item :style="display" label="Confirm password" prop="checkPass">
                <el-input v-model="form.checkPass" :show-password="showpassword" style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="name" prop="name">
                <el-input v-model="form.name" style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="age" prop="age">
                <el-input v-model.number="form.age" style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="gender" prop="gender">
                <el-radio v-model="form.gender" label="男">男</el-radio>
                <el-radio v-model="form.gender" label="女">女</el-radio>
              </el-form-item>
              <el-form-item label="phoneNum" prop="phoneNum">
                <el-input v-model.number="form.phoneNum" style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="email" prop="email">
                <el-input v-model="form.email" style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="dormBuildId" prop="dormBuildId">
                <el-input v-model="form.dormBuildId" style="width: 80%"></el-input>
              </el-form-item>
            </el-form>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="cancel">cancel</el-button>
                <el-button type="primary" @click="save">confirm</el-button>
              </span>
            </template>
          </el-dialog>
        </div>
      </div>
    </el-card>
  </div>
</template>
<script src="@/assets/js/DormManagerInfo.js"></script>