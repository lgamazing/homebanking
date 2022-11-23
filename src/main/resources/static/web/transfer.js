const app = Vue.createApp ({
    data(){
        return{
            url: new URLSearchParams(location.search).get("id"),
            param: `/api/accounts/`,
            urlTransfer: "http://localhost:8080/api/clientes/current",
            amount: 0,
            description: "",
            cuentaOrigen: "",
            cuentaDestino: "",
            clienteActual: [],
            cuentas: [],
            cuentaFiltradas: [],
            cuentaPropia: "",
            cuentaTerceros: "",
        }   
    },
    created(){
        console.log(this.cuentaFiltradas)
    },
    methods:{
        getData(){
            axios.get(this.urlTransfer)
                .then(data => {
                    this.clienteActual = data.data
                    this.cuentas = this.clienteActual.accounts
                })
                .catch(err => console.error(err.message));
        },
        buscarCuenta(array){
            
            let balanceSeleccionado = this.cuentas.filter(cuenta => cuenta.number == array)
            let picado = balanceSeleccionado.map(a => a.balance)
            money = picado
            plata = this.modificarSaldo(money)
            return plata
            
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
        hacerTransaccion(){
            
            return Swal.fire({
                title: 'Esta seguro?',
                text: "No se podra revertir lo hecho!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si, Enviar dinero!'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/transaction',`amount=${this.amount}&description=${this.description}&accountO=${this.cuentaOrigen}&accountD=${this.cuentaDestino}`).catch(function (error) {
                        let errorData = error.response.data
                        Swal.fire({
                        icon: 'error',
                        title: 'CUIDADO!!!!!',
                        text: `${errorData}`,
                      })}
                      )
                  Swal.fire(
                    'Transaccion Completa',
                    'Su dinero ya sido enviado a la cuenta.',
                    'success'
                  ).then(response=> window.location.href = "http://localhost:8080/web/accounts.html")
                }
              })
              
            
        },

    },
    mounted(){
        this.getData()
    },
    computed: {

    },

}).mount("#app");





