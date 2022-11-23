const app = Vue.createApp ({
    data(){
        return{
            url: new URLSearchParams(location.search).get("id"),
            param: `/api/accounts/`,
            account: [],
            cards: [],
            nombre: "",
            DEBITO: [],
            CREDITO: [],
            CREDITOACTIVO: [],
            DEBITOACTIVO: [],
            cardsFiltradas: [],
            cardBorrar: null,
            url3: "http://localhost:8080/api/clientes/current"
        }   
    },
    created(){

    },
    methods:{
        getData(){
            axios.get(this.url3)
                .then(data => {
                    this.account = data.data
                    this.cards = this.account.card 
                    this.nombre = this.account.primerNombre
                    this.cardsFiltradas = this.cards

                    this.DEBITO = this.cardsFiltradas.filter(element => element.type == "DEBITO")
                    this.CREDITO = this.cardsFiltradas.filter(element => element.type == "CREDITO")
                    this.CREDITOACTIVO = this.CREDITO.filter(cuenta => cuenta.active === true)
                    this.DEBITOACTIVO =this.DEBITO.filter(cuenta => cuenta.active === true)
                    
                    console.log(this.CREDITOACTIVO)
                    
                })
                .catch(err => console.error(err.message));
        },

        FechaModificado(fecha){
            FechaMod = fecha.split("T")
            FechOnly = FechaMod[0].split("-")
            return `${FechOnly[1]}-${FechOnly[0]}`
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
        redireccionar(){
            return window.location.href = ("/web/create-cards.html")
        },

        borrarCarta(){
            return Swal.fire({
                title: 'Esta seguro?',
                text: "No se podra revertir lo hecho!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si, Borrar carta!'
              }).then((result) => {
                if (result.isConfirmed) {
                    axios.patch('/api/clients/current/cards/delete',`number=${this.cardBorrar}`).catch(function (error) {
                        let errorData = error.response.data
                        Swal.fire({
                        icon: 'error',
                        title: 'CUIDADO!!!!!',
                        text: `${errorData}`,
                      })}
                      )
                  Swal.fire(
                    'carta Borrada',
                    'Se elemino la carta .',
                    'success'
                  ).then(response=> window.location.href = "http://localhost:8080/web/accounts.html")
                }
              })
        },
        filtrarCartas(){
            this.cardsFiltradas = this.cards.filter(card => card.active === true)
        },

    },
    mounted(){
        this.getData()
    },
    computed(){
        this.filtrarCartas()
    }
}).mount("#app");