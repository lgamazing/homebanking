const app = Vue.createApp ({
    data(){
        return{
            prestamos: "http://localhost:8080/api/loan",
            urlTransfer: "http://localhost:8080/api/clientes/current",
            cuentas: [],


            amount: 0,
            cuentaDestino: "",
            prestamosSolicitado: "",
            cuotasPedidas: 0,


            cuentaFiltradas: [],
            prestamoHipotecario: [],
            cuotasHipoterico: [],
            prestamoPersonal: [],
            cuotasPersonal: [],
            prestamoAutomotriz: [],
            cuotasAutomotriz: [],

        }   
    },
    created(){
        
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
        pedirPrestamo(){
            
            return Swal.fire({
                title: 'Esta seguro?',
                text: "No se podra revertir lo hecho!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si, Solicitar Prestamo!'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.post('/api/loan',
                    {
                        "idLoan": this.prestamosSolicitado,
                        "amount": this.amount,
                        "payments": this.cuotasPedidas,
                        "accountDestini":this.cuentaDestino,

                    }).catch(function (error) {
                        let errorData = error.response.data
                        Swal.fire({
                        icon: 'error',
                        title: 'CUIDADO!!!!!',
                        text: `${errorData}`,
                      })}
                      )
                  Swal.fire(
                    'Prestamo Solicitado',
                    'Su dinero ya ha sido enviado a la cuenta.',
                    'success'
                  ).then(response=> window.location.href = "http://localhost:8080/web/accounts.html")
                }
              })
              
            
        },
        getPrestamos(){
            axios.get(this.prestamos).then(data =>{
                this.prestamosArray = data.data
                this.cuotasHipoterico = this.prestamosArray[0].payments
                this.prestamoHipotecario = this.prestamosArray[0]
                this.cuotasPersonal = this.prestamosArray[1].payments
                this.prestamoPersonal = this.prestamosArray[1]
                this.cuotasAutomotriz = this.prestamosArray[2].payments
                this.prestamoAutomotriz = this.prestamosArray[2]
                console.log(this.prestamosArray)
                console.log(this.prestamoPersonal)
                console.log(this.prestamoAutomotriz)
            })
            .catch(err => console.log(err.message));
        },
        cerrarModal(){
        
            return this.amount = 0 ,this.cuentaDestino = "", this.prestamosSolicitado = "", this.cuotasPedidas = 0
            
        },

        abrirPrestamoHipotecario(){
            return this.prestamosSolicitado = "16"
        },
        abrirPrestamoPersonal(){
            return this.prestamosSolicitado = "17"
        },
        abrirPrestamoAutomotriz(){
            return this.prestamosSolicitado = "18"
        },

        saldoInteres(){
            if(this.prestamosSolicitado != ""){
                if(this.prestamosSolicitado == "16"){
                    saldoInteres = this.amount * 1.2
                }
                if(this.prestamosSolicitado == "17"){
                    saldoInteres = this.amount * 1.1
                }
                if(this.prestamosSolicitado == "18"){
                    saldoInteres = this.amount * 1.15
                }
                return saldoInteres
            }
        },
        cuotas(){
            if(this.prestamosSolicitado != "" && this.cuotasPedidas != ""){
                if(this.prestamosSolicitado == "16"){
                    saldoInteres = this.amount * 1.2
                    cuotas = saldoInteres / this.cuotasPedidas
                }
                if(this.prestamosSolicitado == "17"){
                    saldoInteres = this.amount * 1.2
                    cuotas = saldoInteres / this.cuotasPedidas
                }
                if(this.prestamosSolicitado == "18"){
                    saldoInteres = this.amount * 1.2
                    cuotas = saldoInteres / this.cuotasPedidas
                }
                return cuotas
            }
        },

    },
    mounted(){
        this.getData();
        this.getPrestamos();
    },
    computed: {

    },

}).mount("#app");





