const app = Vue.createApp ({
    data(){
        return{
            maximoAmount: 0,
            namePrestamo: "",
            pagos: [],
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

        LogOut(){
            return axios.post('/api/logout').then(response=> window.location.href = "http://localhost:8080/web/index.html")
        },

        crearLoan(){
            
            return Swal.fire({
                title: 'Esta seguro?',
                text: "No se podra revertir lo hecho!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si, Crear Prestamo!'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/loan/create',
                    {
                        "maxAmount": this.maximoAmount,
                        "name": this.namePrestamo,
                        "payments": [4,6,8,10,12]

                    }).catch(function (error) {
                        let errorData = error.response.data
                        Swal.fire({
                        icon: 'error',
                        title: 'CUIDADO!!!!!',
                        text: `${errorData}`,
                      })}
                      )
                  Swal.fire(
                    'Prestamo Creado',
                    'Prestamo mandado a la base de datos.',
                    'success'
                  ).then(response=> window.location.href = "http://localhost:8080/admin/loan.html")
                }
              })
              
            
        },
    },
    mounted(){
        this.getData()
    },
}).mount("#app");


