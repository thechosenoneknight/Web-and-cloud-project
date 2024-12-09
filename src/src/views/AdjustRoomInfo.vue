<template>
  <div>
    <el-breadcrumb separator-icon="ArrowRight" style="margin: 16px">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>申请管理</el-breadcrumb-item>
      <el-breadcrumb-item>调宿申请</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card style="margin: 15px; min-height: calc(100vh - 111px)">
      <div>
        <!--    功能区-->
        <div style="margin: 10px 0">
          <!--    搜索区-->
          <div style="margin: 10px 0">
            <el-input v-model="search" clearable placeholder="请输入学号" prefix-icon="Search" style="width: 20%"/>
            <el-button icon="Search" style="margin-left: 5px" type="primary" @click="load"></el-button>
            <el-button icon="refresh-left" style="margin-left: 10px" type="default" @click="reset"></el-button>
          </div>
        </div>
        <!--    表格-->
        <el-table v-loading="loading" :data="tableData" border max-height="705" style="width: 100%">
          <el-table-column label="#" type="index"/>
          <el-table-column label="username" prop="username" sortable width="100px"/>
          <el-table-column label="name" prop="name" width="100px"/>
          <el-table-column label="currentRoom" prop="currentRoomId" sortable/>
          <el-table-column label="currentBed" prop="currentBedId" sortable/>
          <el-table-column label="towardsRoom" prop="towardsRoomId" sortable/>
          <el-table-column label="towardsBed" prop="towardsBedId" sortable/>
          <el-table-column
              :filter-method="filterTag"
              :filters="[
              { text: 'untreated', value: '未处理' },
              { text: 'agree', value: '通过' },
              { text: 'refuse', value: '驳回' },
            ]"
              filter-placement="bottom-end"
              label="application status"
              prop="state"
              sortable
              width="130px"
          >
            <template #default="scope">
              <el-tag :type="scope.row.state === '通过' ? 'success' : (scope.row.state === '驳回' ? 'danger' : 'info')"
                      disable-transitions
              >{{ scope.row.state }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="applyTime" prop="applyTime" sortable/>
          <el-table-column label="finishTime" prop="finishTime" sortable/>
          <!--      操作栏-->
          <el-table-column label="operation" width="190px">
            <template #default="scope">
              <el-button v-if="scope.row.state==='通过' ||scope.row.state==='驳回'" icon="more-filled" type="default"
                         @click="showDetail(scope.row)"></el-button>
              <el-button v-if="scope.row.state!=='通过' " icon="Edit" type="primary"
                         @click="handleEdit(scope.row)"></el-button>
              <el-popconfirm title="confirmDelete？" @confirm="handleDelete(scope.row.id)">
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
          <el-dialog v-model="dialogVisible" title="操作" width="30%" @close="cancel">
            <el-form ref="form" :model="form" :rules="rules" label-width="120px">
              <el-form-item label="username" prop="username">
                <el-input v-model="form.username" disabled style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="name" prop="name">
                <el-input v-model="form.name" disabled style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item disabled label="currentRoomId" prop="currentRoomId">
                <el-input v-model="form.currentRoomId" disabled style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="currentBedId" prop="currentBedId">
                <el-input v-model="form.currentBedId" disabled style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="towardsRoomId" prop="towardsRoomId">
                <el-input v-model="form.towardsRoomId" style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="towardsBedId" prop="towardsBedId">
                <el-input v-model="form.towardsBedId" style="width: 80%"></el-input>
              </el-form-item>
              <el-form-item label="applyTime" prop="applyTime" style="margin-top: 27px">
                <el-date-picker
                    v-model="form.applyTime"
                    clearable
                    disabled
                    placeholder="choosetime"
                    style="width: 50%"
                    type="datetime"
                    value-format="YYYY-MM-DD HH:mm:ss"
                ></el-date-picker>
              </el-form-item>
              <el-form-item label="state" prop="state">
                <el-radio v-model="form.state" label="agree">通过</el-radio>
                <el-radio v-if="this.form.state!=='通过'" v-model="form.state" label="refuse">驳回</el-radio>
              </el-form-item>
              <el-form-item label="process time" prop="finishTime" style="margin-top: 27px">
                <el-date-picker
                    v-model="form.finishTime"
                    clearable
                    placeholder="choose time"
                    style="width: 50%"
                    type="datetime"
                    value-format="YYYY-MM-DD HH:mm:ss"
                ></el-date-picker>
              </el-form-item>
            </el-form>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="cancel">cancel</el-button>
                <el-button type="primary" @click="save">confirm</el-button>
              </span>
            </template>
          </el-dialog>
          <!--详情信息弹窗-->
          <el-dialog v-model="detailDialog" title="info" width="30%" @close="cancel">
            <el-form ref="form" :model="form" label-width="220px">
              <el-form-item label="username：" prop="username">
                <template #default="scope">
                  <span>{{ form.username }}</span>
                </template>
              </el-form-item>
              <el-form-item label="name：" prop="name">
                <template #default="scope">
                  <span>{{ form.name }}</span>
                </template>
              </el-form-item>
              <el-form-item label="currentRoomId：" prop="currentRoomId">
                <template #default="scope">
                  <span>{{ form.currentRoomId }}</span>
                </template>
              </el-form-item>
              <el-form-item label="currentBedId：" prop="currentBedId">
                <template #default="scope">
                  <span>{{ form.currentBedId }}</span>
                </template>
              </el-form-item>
              <el-form-item label="towardsRoomId：" prop="towardsRoomId">
                <template #default="scope">
                  <span>{{ form.towardsRoomId }}</span>
                </template>
              </el-form-item>
              <el-form-item label="towardsBedId：" prop="towardsBedId">
                <template #default="scope">
                  <span>{{ form.towardsBedId }}</span>
                </template>
              </el-form-item>
              <el-form-item label="applyTime：" prop="applyTime">
                <template #default="scope">
                  <span>{{ form.applyTime }}</span>
                </template>
              </el-form-item>
              <el-form-item label="state：" prop="state">
                <template #default="scope">
                  <span>{{ form.state }}</span>
                </template>
              </el-form-item>
              <el-form-item label="finishTime：" prop="finishTime">
                <template #default="scope">
                  <span>{{ form.finishTime }}</span>
                </template>
              </el-form-item>
            </el-form>
          </el-dialog>
        </div>
      </div>
    </el-card>
  </div>
</template>
<script src="@/assets/js/AdjustRoomInfo.js"></script>