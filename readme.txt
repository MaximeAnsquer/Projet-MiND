Le logiciel n'est pas entièrement terminé, nous nous excusons pour ce retard. 

Afin que celui-ci fonctionne, il est nécessaire que le fichier OutilAnalyseDeRisque.jar soit situé dans le même dossier
que le fichier bdc.xml, le dossier images et le dossier etudes.
Ces deux derniers dossiers et leurs contenus ne doivent pas être modifiés. Le fichier bdc.xml peut lui être modifié à 
à condition de respecter la syntaxe qu'il propose (celle-ci pourra d'ailleurs être homogénéisée par nos soins).

L'utilisation du logiciel est, il nous semble, suffisament intuitive et proche des mécaniques décrites dans le cahier 
des charges pour que l'on puisse se passer d'un mode d'emploi (celui-ci pourra bien sûr être rédigé si le besoin s'en
fait sentir).

Une erreur survient lors de l'ouverture d'une etude sauvegardée, mais nous connaissons l'origine du problème et savons
comment le résoudre.

Quatre modules ne sont pas encore opérationnels (" Module manquant ").

Il nous semble que le " workflow " qui nous a été transmis et sur lequel nous nous basons n'est pas
entièrement cohérent. En effet, nous pensons que le module " Critères de sécurité " est nécessaire à la réalisation du
module " Scénarios de menaces génériques ". Or ce lien n'est pas représenté sur le workflow fourni. Cette contrainte
supplémentaire est présente dans le logiciel (impossibilité de créer le module " Scénarios de menaces génériques "
si le module " Critères de sécurité " n'est pas créé et cohérent), mais il conviendrait d'adapter le schéma du 
workflow en conséquence pour plus de cohérence.

Les accents, caractères spéciaux manquants et autres fautes d'orthographe seront corrigés.

Le code source est voué à être organisé et commenté de façon plus appropriée. Des maladresses techniques pourront également
être évitées. La classe principale appellée lors du lancement du programme est MainMaximeAnsquer.java, présente dans 
le package presentation.