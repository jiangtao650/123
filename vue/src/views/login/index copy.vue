<div class="changeMain">
      <p class="changeTips">密码设置规则</p>
      <p class="changeTips changeRules">6-16位字符，同时包括数字、大小写字母和特殊字符四种组合； 特殊字符需在“~ @ # $ % * _ - + = : , . ?”范围内选择。</p>
      <Form ref="formValidate" class="changeForm" :model="formValidate" :rules="ruleValidate" :label-width="80">
         <FormItem label="新密码" prop="password" :label-width="100">
             <Input v-model="formValidate.password" class="changeInput" type="password" placeholder="请输入密码"></Input>
             <Icon type="ios-checkmark-circle" v-if="changeFlag == 1" color="#35B449"/>
             <Icon type="md-close-circle" v-else-if="changeFlag == 2" color="#f00"/>
         </FormItem>
         <FormItem label="重复密码" prop="againPassword" :label-width="100">
             <Input v-model="formValidate.againPassword" class="changeInput" type="password" placeholder="请输入密码"></Input>
             <Icon type="ios-checkmark-circle" v-if="changeAgainFlag == 1" color="#35B449"/>
             <Icon type="md-close-circle" v-else-if="changeAgainFlag == 2" color="#f00"/>
         </FormItem>
         <FormItem class="changeBtnbox">
            <Button type="primary">确定</Button>
            <Button style="margin-left: 30px">取消</Button>
        </FormItem>
     </Form>
    </div>
export default {
  data () {
    // 密码验证
    const pwdCheck = async(rule, value, callback) => {
      let reg = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[~@#$%\*-\+=:,\\?\[\]\{}]).{6,16}$/
      if (value.length < 6) {
        this.changeFlag = 2;
        return callback(new Error('密码不能少于6位！'));
      } else if (value.length > 16) {
        this.changeFlag = 2;
        return callback(new Error('密码最长不能超过16位！'));
      } else if (!reg.test(value)) {
        this.changeFlag = 2;
        return callback(new Error('密码输入有误，请检查格式是否正确！'));
      } else {
        this.changeFlag = 1;
        callback()
      }
    }
    // 重复密码验证
    const pwdAgainCheck = async(rule, value, callback) => {
      if (value.length < 1) {
        this.changeAgainFlag = 2;
        return callback(new Error('重复密码不能为空！'));
      } else if(this.formValidate.password != this.formValidate.againPassword){
        this.changeAgainFlag = 2;
        return callback(new Error('两次输入密码不一致！'));
      }else{
        this.changeAgainFlag = 1;
        callback()
      }
    };
    return {
      changeFlag: 0,
      changeAgainFlag: 0,
      formValidate: {
          password: '',
          againPassword: ''
      },
      ruleValidate: {
          password: [
              { required: true, validator: pwdCheck, trigger: 'blur' }
          ],
          againPassword: [
              { required: true, validator: pwdAgainCheck, trigger: 'blur' }
          ]
      }
    }
  },
  methods: {
      handleSubmit (name) {
          this.$refs[name].validate((valid) => {
              if (valid) {
                  this.$Message.success('Success!');
              } else {
                  this.$Message.error('Fail!');
              }
          })
      },
      handleReset (name) {
          this.$refs[name].resetFields();
      }
  }
}