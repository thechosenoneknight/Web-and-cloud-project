<template>
  <div>
    <el-breadcrumb separator-icon="ArrowRight" style="margin: 16px">
      <el-breadcrumb-item :to="{ path: '/home' }">index</el-breadcrumb-item>
      <el-breadcrumb-item>Personal Information</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card style="margin: 15px; min-height: calc(100vh - 111px)">
      <div style="display: flex">
        <div style="width: 600px; margin-left: 30px; position: relative">
          <div>
            <el-upload :on-success="uploadSuccess" :show-file-list="false"
                       action="http://localhost:9090/files/upload/"
                       class="upload-demo"
            >
              <div class="AvatarDiv">
                <el-avatar icon="UserFilled" style="width: 80px; height: 80px"></el-avatar>
                <img :src="'data:image;base64,' + image" :style="imgDisplay"
                     style="width: 80px; height: 80px; border-radius: 40px"/>
                <div class="editImg">
                  change the avatar
                  <el-icon color="color">
                    <edit></edit>
                  </el-icon>
                </div>
              </div>
            </el-upload>
          </div>
          <el-descriptions :column="1" :size="large" border style="min-width: 500px" title="">
            <el-descriptions-item>
              <template #label>
                <div>
                  <el-icon>
                    <user/>
                  </el-icon>
                  username
                </div>
              </template>
              {{ username }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div>
                  <el-icon>
                    <location/>
                  </el-icon>
                  name
                </div>
              </template>
              {{ name }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div>
                  <el-icon>
                    <tickets/>
                  </el-icon>
                  gender
                </div>
              </template>
              {{ gender }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div>
                  <el-icon>
                    <office-building/>
                  </el-icon>
                  age
                </div>
              </template>
              {{ age }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div>
                  <el-icon>
                    <iphone/>
                  </el-icon>
                  phoneNum
                </div>
              </template>
              {{ phoneNum }}
            </el-descriptions-item>
            <el-descriptions-item>
              <template #label>
                <div>
                  <el-icon>
                    <office-building/>
                  </el-icon>
                  email
                </div>
              </template>
              {{ email }}
            </el-descriptions-item>
          </el-descriptions>
          <el-tooltip content="change info" placement="bottom">
            <el-button icon="Edit" size="large" style="margin-top: 30px; width: 80px" type="primary" @click="Edit">
            </el-button>
          </el-tooltip>
        </div>
        <div style="margin-left: 130px">
          <img alt="" src="../../public/self_Space.png"/>
        </div>
      </div>
      <div>
        <!--      弹窗-->
        <el-dialog v-model="dialogVisible" title="operation" width="30%" @close="cancel">
          <el-form ref="form" :model="form" :rules="rules" label-width="120px">
            <el-form-item label="username" prop="username">
              <el-input v-model="form.username" disabled style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="password" prop="password">
              <el-input v-model="form.password" :disabled="disabled" :show-password="showpassword" show-password
                        style="width: 80%"></el-input>
              <el-tooltip content="Change Password" placement="right">
                <el-icon size="large" style="margin-left: 5px; cursor: pointer" @click="EditPass">
                  <edit/>
                </el-icon>
              </el-tooltip>
            </el-form-item>
            <el-form-item :style="display" label="checkPass" prop="checkPass">
              <el-input v-model="form.checkPass" show-password style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="name" prop="name">
              <el-input v-model="form.name" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="gender" prop="gender">
              <el-radio v-model="form.gender" label="男">男</el-radio>
              <el-radio v-model="form.gender" label="女">女</el-radio>
            </el-form-item>
            <el-form-item label="age" prop="age">
              <el-input v-model.number="form.age" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="phoneNum" prop="phoneNum">
              <el-input v-model.number="form.phoneNum" style="width: 80%"></el-input>
            </el-form-item>
            <el-form-item label="email" prop="email">
              <el-input v-model="form.email" style="width: 80%"></el-input>
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
    </el-card>
  </div>
</template>
<script src="@/assets/js/SelfInfo.js"></script>
<style scoped>@import '../assets/css/SelfInfo.css';</style>