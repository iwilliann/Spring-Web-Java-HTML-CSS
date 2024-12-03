document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("userForm");
    if (form) {
        form.addEventListener("submit", async (event) => {
            event.preventDefault();

            const username = document.getElementById("username").value;
            const senha = document.getElementById("password").value;
            const confirmSenha = document.getElementById("confirm-password").value;

            if (senha !== confirmSenha) {
                alert("As senhas não coincidem!");
                return;
            }

            const usuario = {
                username: username,
                senha: senha,
                permissaoUsuario: "USER" // Define uma permissão padrão
            };

            try {
                const response = await fetch("/api/usuarios", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(usuario)
                });

                if (response.ok) {
                    alert("Usuário cadastrado com sucesso!");
                    window.location.href = "/login"; // Redireciona para a página de login
                } else {
                    const error = await response.json();
                    alert("Erro ao cadastrar usuário: " + (error.message || JSON.stringify(error)));
                }
            } catch (error) {
                console.error("Erro ao enviar os dados:", error);
                alert("Erro ao enviar os dados para o servidor.");
            }
        });
    }
});
