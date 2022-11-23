const app = Vue.createApp ({
    data(){
        return{
            url: new URLSearchParams(location.search).get("id"),
            param: `/api/accounts/`,
    
            account: [],
            transaccion: [],
            nombre: [],
        }   
    },
    created(){

    },
    methods:{
        getData(){
            axios.get(this.param + this.url)
                .then(data => {
                    this.account = data.data
                    this.transaccion = this.account.transactions 
                    console.log(this.account)
                    
                })
                .catch(err => console.error(err.message));
        },

        FechaModificado(fecha){
            FechaMod = fecha.split("T")
            FechOnly = FechaMod[0].split("-")
            return `${FechOnly[2]}-${FechOnly[1]}-${FechOnly[0]}`
        },
        modificarSaldo(saldo){
            return new Intl.NumberFormat('ar-AR', { style: 'currency', currency: 'ARS' }).format(saldo);
        },
        sortArrays(arrays) {
            return arrays.sort((a, b) => a.id - b.id ).reverse()
        },
        LogOut(){
            return axios.post('/api/logout').then(response=> window.location.href = "http://localhost:8080/web/index.html")
        },
    },
    mounted(){
        this.getData()
    },
}).mount("#app");


