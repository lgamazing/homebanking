const app = Vue.createApp ({
    data(){
        return{
            urlApi: "http://localhost:8080/api/clientes/current",
            titulo: "Funca",
            cliente: [],
            cuenta: [],
            prestamo: [],
            cuentasActivas: [],
            cuentaBorrar: null,
        }
    },
    created(){
        
    },
    methods:{
        getData(){
            axios.get(this.urlApi)
                .then(data => {
                    this.cliente = data.data
                   
                    this.cuenta = this.cliente.accounts
                    this.prestamo = this.cliente.loans
                    this.cuentasActivas = this.cuenta
                    

                    /* this.filtrarCuentas() */
                    console.log(this.cuentasActivas)
                })
                .catch(err => console.error(err.message));
        },
        crearCuenta(){
            return axios.post("/api/clientes/current/accounts",'type=CORRIENTE').then(response=> window.location.href = "http://localhost:8080/web/accounts.html")
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
            return arrays.sort((a, b) => a.id - b.id)
        },
        LogOut(){
            return axios.post('/api/logout').then(response=> window.location.href = "http://localhost:8080/web/index.html")
        },
        BorrarCuenta(){
            return Swal.fire({
                title: 'Esta seguro?',
                text: "No se podra revertir lo hecho!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si, Borrar cuenta!'
              }).then((result) => {
                if (result.isConfirmed) {
                     axios.patch('/api/clients/current/accounts/delete',`number=${this.cuentaBorrar}`).catch(function (error) {
                        let errorData = error.response.data
                        Swal.fire({
                        icon: 'error',
                        title: 'CUIDADO!!!!!',
                        text: `${errorData}`,
                      })}
                      )
                  Swal.fire(
                    'Cuenta Borrada',
                    'Se elemino la cuenta .',
                    'success'
                  ).then(response=> window.location.href = "http://localhost:8080/web/accounts.html")
                }
              })
        },
        filtrarCuentas(){
            this.cuentasActivas = this.cuenta.filter(cuenta => cuenta.active === true)
        },
    },
    mounted(){
        this.getData()
        
        
    },
    computed() {
        this.filtrarCuentas()
    }

}).mount("#app");


