document.addEventListener("DOMContentLoaded", function () {
    fetch("/cadastroUsuario/generos")
        .then(response => response.json())
        .then(data => {
            const ctx = document.getElementById("graficoGenero").getContext("2d");
            new Chart(ctx, {
                type: "pie",
                data: {
                    labels: Object.keys(data),
                    datasets: [{
                        label: "Distribuição por Gênero",
                        data: Object.values(data),
                        backgroundColor: ["#3498db", "#e74c3c", "#9b59b6"],
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true
                }
            });
        })
        .catch(error => console.error("Erro ao carregar os dados do gráfico:", error));
});
