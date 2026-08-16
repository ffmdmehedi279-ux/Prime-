package com.example.model

data class OptimizationItem(
  val id: String,
  val title: String,
  val description: String,
  val isChecked: Boolean = true,
  val hasQuickAction: Boolean = false,
  val iconName: String = "ram"
)

data class OptimizationState(
  val ramCleanedMb: Int = 480,
  val totalRamMb: Int = 8192,
  val optimizationScore: Int = 87,
  val isOptimizing: Boolean = false,
  val lastOptimizedTime: String = "Hoje às 16:02",
  val items: List<OptimizationItem> = listOf(
    OptimizationItem(
      id = "ram",
      title = "Otimizar Memória RAM",
      description = "Libera memória RAM fechando processos em segundo plano e limpando resíduos de cache.",
      isChecked = true,
      hasQuickAction = true,
      iconName = "ram"
    ),
    OptimizationItem(
      id = "device",
      title = "Otimizar Dispositivo",
      description = "Aumenta a estabilidade de resposta ao toque e reduz o atraso de quadros.",
      isChecked = true,
      hasQuickAction = false,
      iconName = "phone"
    ),
    OptimizationItem(
      id = "freeze",
      title = "Reduzir Travamentos",
      description = "Gerencia arquivos de log e buffers desnecessários para prevenir quedas bruscas de FPS.",
      isChecked = true,
      hasQuickAction = false,
      iconName = "flash"
    ),
    OptimizationItem(
      id = "fps",
      title = "FPS Booster & Display Sync",
      description = "Ajusta prioridade de renderização da tela e bloqueio de taxa de atualização.",
      isChecked = true,
      hasQuickAction = false,
      iconName = "speed"
    ),
    OptimizationItem(
      id = "network",
      title = "Estabilidade de Ping & Rede",
      description = "Reduz interferências de buffer de rede e previne perda de pacotes durante troca de tiros.",
      isChecked = false,
      hasQuickAction = false,
      iconName = "wifi"
    )
  )
)
