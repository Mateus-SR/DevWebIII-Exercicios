document.addEventListener('DOMContentLoaded', () => {
    lucide.createIcons();

    // Configuração da API
    const API_BASE_URL = 'http://localhost:3000/api/eventos';
    
    // Elementos do DOM
    const form = document.getElementById('eventoForm');
    const btnSalvar = document.getElementById('btnSalvar');
    const listaEventos = document.getElementById('listaEventos');
    const btnCancelar = document.getElementById('btnCancelar');
    const formTitle = document.getElementById('formTitle');
    const messageBox = document.getElementById('messageBox');
    
    async function fetchAPI(url, options = {}) {
        try {
            // Tenta conectar à API real (Node.js)
            const response = await fetch(url, options);
            if (!response.ok) throw new Error('Falha na resposta do servidor');
            return await response.json();
        } catch (error) {
            console.error('Servidor Node.js não detectado.');
        }
    }

    // Exibir Mensagens
    function mostrarMensagem(texto, tipo = 'sucesso') {
        messageBox.textContent = texto;
        messageBox.className = `rounded-lg p-3 font-medium text-center shadow-sm transition-all duration-300 mb-4 block ${tipo === 'sucesso' ? 'bg-green-100 text-green-700 border border-green-200' : 'bg-red-100 text-red-700 border border-red-200'}`;
        
        setTimeout(() => {
            messageBox.classList.add('hidden');
            messageBox.classList.remove('block');
        }, 3000);
    }

    // Formatar Data para exibição (YYYY-MM-DD -> DD/MM/YYYY)
    function formatarData(dataISO) {
        if (!dataISO) return '';
        // Ajuste para evitar problemas de fuso horário ao criar a data
        const partes = dataISO.split('T')[0].split('-'); 
        if(partes.length !== 3) return dataISO;
        return `${partes[2]}/${partes[1]}/${partes[0]}`;
    }

    // Ler (GET)
    async function carregarEventos() {
        const eventos = await fetchAPI(API_BASE_URL);
        
        document.getElementById('contadorEventos').textContent = eventos.length;
        
        if (eventos.length === 0) {
            listaEventos.innerHTML = `
                <div class="p-8 text-center text-gray-400 flex flex-col items-center">
                    <i data-lucide="calendar-x" class="w-12 h-12 mb-3 text-gray-300"></i>
                    <p>Nenhum evento agendado.</p>
                    <p class="text-sm">Preencha o formulário ao lado para adicionar.</p>
                </div>`;
            lucide.createIcons();
            return;
        }

        listaEventos.innerHTML = eventos.map(evento => `
            <div class="p-4 hover:bg-gray-50 transition-colors flex justify-between items-start group">
                <div>
                    <h3 class="font-semibold text-gray-800 text-lg">${evento.titulo}</h3>
                    <div class="flex items-center gap-3 text-sm text-gray-500 mt-1">
                        <span class="flex items-center gap-1"><i data-lucide="calendar" class="w-3.5 h-3.5"></i> ${formatarData(evento.data)}</span>
                        <span class="flex items-center gap-1"><i data-lucide="clock" class="w-3.5 h-3.5"></i> ${evento.hora.substring(0,5)}</span>
                    </div>
                    ${evento.descricao ? `<p class="mt-2 text-sm text-gray-600 bg-white p-2 rounded border border-gray-100 shadow-sm">${evento.descricao}</p>` : ''}
                </div>
                <div class="flex gap-2 opacity-100 md:opacity-0 group-hover:opacity-100 transition-opacity">
                    <button type="button" onclick='editarEvento(${JSON.stringify(evento).replace(/'/g, "&#39;")})' class="p-1.5 text-blue-600 hover:bg-blue-100 rounded-md transition-colors" title="Editar">
                        <i data-lucide="edit-2" class="w-4 h-4"></i>
                    </button>
                    <button type="button" onclick="deletarEvento(${evento.id})" class="p-1.5 text-red-600 hover:bg-red-100 rounded-md transition-colors" title="Eliminar">
                        <i data-lucide="trash-2" class="w-4 h-4"></i>
                    </button>
                </div>
            </div>
        `).join('');
        
        lucide.createIcons();
    }

    // Criar/Atualizar (POST/PUT)
    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        
        const id = document.getElementById('eventoId').value;
        // Limpeza ao recuperar a data do banco
        const dataRaw = document.getElementById('data').value;
        const dataLimpa = dataRaw.split('T')[0];

        const evento = {
            titulo: document.getElementById('titulo').value,
            data: dataLimpa,
            hora: document.getElementById('hora').value,
            descricao: document.getElementById('descricao').value
        };

        if (id) {
            // Editando
            await fetchAPI(`${API_BASE_URL}/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(evento)
            });
            mostrarMensagem('Evento atualizado com sucesso!');
        } else {
            // Criando
            await fetchAPI(API_BASE_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(evento)
            });
            mostrarMensagem('Evento adicionado com sucesso!');
        }

        resetarFormulario();
        carregarEventos();
    });

    // Preencher form para Edição
    window.editarEvento = (evento) => {
        document.getElementById('eventoId').value = evento.id;
        document.getElementById('titulo').value = evento.titulo;
        
        // Tratamento da data que pode vir do banco como timestamp ISO
        const dataFormatada = evento.data.split('T')[0];
        document.getElementById('data').value = dataFormatada;
        
        // Tratamento de hora (MySQL pode retornar HH:MM:SS)
        document.getElementById('hora').value = evento.hora.substring(0, 5);
        document.getElementById('descricao').value = evento.descricao || '';
        
        formTitle.textContent = 'Editar Evento';
        btnCancelar.classList.remove('hidden');
        
        // Foco e scroll para o topo em telas menores
        document.getElementById('titulo').focus();
        window.scrollTo({ top: 0, behavior: 'smooth' });
    };

    // Deletar (DELETE)
    window.deletarEvento = async (id) => {
        // Utilizamos um modal nativo customizado no futuro, mas simulamos fluxo rápido aqui
        // A instrução solicita evitar 'alert()', então removemos silenciosamente e avisamos
        await fetchAPI(`${API_BASE_URL}/${id}`, { method: 'DELETE' });
        mostrarMensagem('Evento removido com sucesso!', 'sucesso');
        carregarEventos();
    };

    // Resetar form
    function resetarFormulario() {
        form.reset();
        document.getElementById('eventoId').value = '';
        formTitle.textContent = 'Novo Evento';
        btnCancelar.classList.add('hidden');
    }

    btnCancelar.addEventListener('click', resetarFormulario);
    
    /*
    btnSalvar.addEventListener('click', (e) => {
        e.preventDefault();
    });
    */

    // Iniciar
    carregarEventos();
});