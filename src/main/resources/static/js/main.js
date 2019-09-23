function getIndex(list,id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id){
            return i
        }
    }
    return -1;
}
var messageApi = Vue.resource('/message{/id}');

Vue.component('user-form',{
    props:['usrs','msg'],
    data: function(){
      return {
          text: '',
          id:''
      }
    },

    template:
        '<div> ' +
            '<input type="text" placeholder="Write name" v-model="text">' +
            '<input type="button" value="Save" @click="save">' +
        '</div>',
    watch:{
      msg: function (newVal, oldVal) {
          this.text = newVal.text;
          this.id = newVal.id
      }
    },
    methods:{
        save: function () {

            if (this.id){
                var msg = {text:this.text}
                messageApi.update({id:this.id},msg).then(result =>{
                    result.json().then(data =>{
                        var index = getIndex(this.usrs,data.id);
                        console.log(data);
                        this.usrs.splice(index, 1, data);

                    })
                })
            }else {
                var msg = {text: this.text};
                messageApi.save({}, msg).then(response =>
                    response.json().then(data => {
                            this.usrs.push(data);
                            this.text = ''
                        }
                    ))
            }
        }
    }
});

Vue.component('user-row',{
        props: ['user','usrs','editMethod'],
        template:
            '<div> ' +
                '<i>({{user.id}})</i> {{user.text}} ' +
            '<span style="position: relative; right: 0">' +
                '<input type="button" value="Edit" @click="edit"> ' +
                '<input type="button" value="X" @click="del">' +
            '</span>' +
            '</div>',
    methods:{
            edit: function () {
                this.editMethod(this.user)
            },
            del: function () {
                messageApi.delete({id:this.user.id}).then(result => {
                    if (result.ok){
                        this.usrs.splice(this.usrs.indexOf(this.user), 1)
                    }
                })
            }
    }
    
        
    }
);

Vue.component('users', {
    props: ['usrs'],
    data: function(){
        return {user: null}
    },
    template:
        '<div style="position: relative; width: 300px">' +
        '<user-form :usrs="usrs" :msg="user"/>' +
        '<user-row  v-for="user in usrs" :user="user" :key="user.id" :usrs="usrs" :editMethod="editMethod"> </user-row>' +
        '</div>',
    created: function () {
        messageApi.get().then(result =>
            result.json().then(data =>
                data.forEach(user => this.usrs.push(user)))
        )

    },
    methods:{
        editMethod: function (msg) {
            this.user = msg
        }
    }
});


var app2 = new Vue({
    el: '#app-2',
    template: '<users :usrs="users"/>',
    data: {
        users: []
    }

});