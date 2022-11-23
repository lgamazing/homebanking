
const app = Vue.createApp ({
    data() {
        return{
            urlApi: "http://localhost:8080/rest/clientes",
            clientes: [],
            titulo: "",
            name: "",
            apellido: "",
            email: "",
        };
    },
    created(){
    },
    methods:{
        getdata(){
            axios.get("http://localhost:8080/rest/clientes")
            .then(data => {
                this.clientes = data.data._embedded.clientes
                console.log(this.clientes)

            })
            .catch(err => console.error(err.message));
        },
        agregarUsuario(){
            axios.post("http://localhost:8080/rest/clientes", {
                primerNombre: this.name,
                apellido: this.apellido,
                email: this.email,

            }).then(()=> location.reload())
        },
        QuitarUsuario(id){
            axios.delete(id._links.cliente.href).then(() => {
                this.getdata();
            })
        },
        editarUsuario(){
            axios.patch()
        }
    },
    computed:{
    },
    mounted(){
        this.getdata()
    },
    validations:{
        clientes: {
            primerNombre: {

            }
        }
    }
}).mount("#app");
