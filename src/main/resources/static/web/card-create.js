const app = Vue.createApp ({
    data(){
        return{
            cards: [],
            nombre: "",
            apellido: "",
            nombreCompleto: "",
            url3: "http://localhost:8080/api/clientes/current",
            tiempoTranscurrido: new Date(),
            color: "",
            tipo: "",
            tarjetasCredito: [],
            tarjetasDebito: [],

            
        }   
    },
    created(){

    },
    methods:{
        getData(){
            axios.get(this.url3)
                .then(data => {
                    this.data = data.data
                    this.nombre = this.data.primerNombre
                    this.apellido = this.data.apellido
                    this.nombreCompleto = this.nombre + " " + this.apellido
                    this.tarjetasCredito = data.data.cards.filter(card => card.Type == "CREDITO")
                    this.tarjetasDebito = data.data.cards.filter(card => card.Type == "DEBITO")
                    console.log(this.nombre)
                    console.log(this.apellido)
                    console.log(this.nombreCompleto)
                    
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
        modificar(array){
            return array.getMonth() +1 + "/" + array.getFullYear()
        },
        crearCarta(){
            
            return axios.post('/api/clientes/current/cards',`type=${this.tipo}&color=${this.color}`).then(response => Swal.fire({
                title: 'Card created',
                showDenyButton: true,
                showCancelButton: true,
                confirmButtonText: 'back to cards',
                denyButtonText: `Don't go to cards`,
              }).then((result) => {
                /* Read more about isConfirmed, isDenied below */
                if (result.isConfirmed) {
                  Swal.fire('Ok!', '', 'success').then(response=> window.location.href = "http://localhost:8080/web/card.html")
                } else if (result.isDenied) {
                  Swal.fire('Well Do another Card', '', 'info')
                }
              }))
            .catch(function (error) {
                let errorData = error.response.data
                Swal.fire({
                icon: 'error',
                title: 'Cuidado!!',
                text: `${errorData}`,
              })}
              )
            
        }
    },
    mounted(){
        this.getData()
    },
}).mount("#app");

