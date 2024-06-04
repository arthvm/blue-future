package br.com.bluefuture.main;

import br.com.bluefuture.bean.*;

import java.util.List;
import java.util.Scanner;

public class Test {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean isRunning = true;

        while (isRunning){
            System.out.print("""
                    1. Signup --> Crie a sua conta aqui
                    2. Login  --> Acesse a sua conta aqui
                    3. Encerrar
                    >>\s""");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice){
                    case 1:
                        signUpMenu();
                        break;
                    case 2:
                        loginMenu();
                        break;
                    case 3:
                        isRunning = false;
                        break;
                    default:
                        System.out.println("Opcao invalida!");
                        break;
                }
            }catch (Exception e){
                System.out.println("Something went wrong!");
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Encerrando...");
    }

    private static void signUpMenu() {
        boolean isSigningUp = true;

        while (isSigningUp){
            System.out.print("""
                    1. Usuario Comum --> Crie uma conta como usuario comum para reportar lixos e participar de eventos
                    2. Organizacao   --> Crie uma conta como organizacao para receber alerta de lixos e criar eventos
                    3. Cancelar
                    >>\s""");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> {
                        createAccount();
                        isSigningUp = false;
                    }
                    case 2 -> {
                        System.out.print("Qual o CEP da sede da sua organizacao?\n>> ");
                        createAccount(scanner.nextLine());
                        isSigningUp = false;
                    }
                    case 3 -> isSigningUp = false;
                    default -> System.out.println("Opcao invalida!");
                }
            }catch (Exception e){
                System.out.println("Something went wrong!");
                System.out.println("Error: " + e.getMessage());
            }
        }
        System.out.println("Retornando ao menu principal...");
    }

    private static void createAccount() {
        boolean isMissingInfo = true;
        User user = new User();

        while(isMissingInfo){
            if (user.getName() == null) {
                try{System.out.print("Qual o seu nome?\n>> ");
                    user.setName(scanner.nextLine());
                }catch (Exception e){
                    System.out.println("Something went wrong!");
                    System.out.println("Error: " + e.getMessage());
                }
            }

            if (user.getEmail() == null) {
                try {
                    System.out.print("Qual o seu email?\n>> ");
                    user.setEmail(scanner.nextLine());
                }catch (Exception e){
                    System.out.println("Something went wrong!");
                    System.out.println("Error: " + e.getMessage());
                }
            }

            if (user.getPassword() == null) {
                try {
                    System.out.print("Defina uma senha:\n>> ");
                    user.setPassword(scanner.nextLine());
                }catch (Exception e){
                    System.out.println("Something went wrong!");
                    System.out.println("Error: " + e.getMessage());
                }
            }

            if(user.getName() == null || user.getEmail() == null || user.getPassword() == null) continue;

            try{
                AccountManager.register(user);
                isMissingInfo = false;
                System.out.println("Usuario cadastrado com sucesso!");
            }catch (Exception e){
                System.out.println(e.getMessage());
                break;
            }
        }
    }

    private static void createAccount(String location) {
        boolean isMissingInfo = true;
        Organization organization = new Organization();
        organization.setLocation(location);

        while(isMissingInfo){
            if (organization.getName() == null) {
                try{
                    System.out.print("Qual o nome da organizacao?\n>> ");
                    organization.setName(scanner.nextLine());
                }catch (Exception e){
                    System.out.println("Something went wrong!");
                    System.out.println("Error: " + e.getMessage());
                }
            }

            if (organization.getEmail() == null) {
                try {
                    System.out.print("Qual o email da organizacao?\n>> ");
                    organization.setEmail(scanner.nextLine());
                }catch (Exception e){
                    System.out.println("Something went wrong!");
                    System.out.println("Error: " + e.getMessage());
                }
            }

            if (organization.getPassword() == null) {
                try {
                    System.out.print("Defina uma senha:\n>> ");
                    organization.setPassword(scanner.nextLine());
                }catch (Exception e){
                    System.out.println("Something went wrong!");
                    System.out.println("Error: " + e.getMessage());
                }
            }

            if(organization.getName() == null || organization.getEmail() == null || organization.getPassword() == null) continue;

            try{
                AccountManager.register(organization);
                isMissingInfo = false;
                System.out.println("Usuario cadastrado com sucesso!");
            }catch (Exception e){
                System.out.println(e.getMessage());
                break;
            }
        }
    }

    private static void loginMenu() {
        boolean isLoggingIn = true;

        while (isLoggingIn) {
            System.out.print("Informe o seu email:\n>> ");
            String email = scanner.nextLine();
            System.out.print("Informe a sua senha:\n>> ");
            String password = scanner.nextLine();

            try {
                Account account = AccountManager.login(email, password);

                if(account instanceof User) userMenu((User)account);
                if(account instanceof Organization) organizationMenu((Organization) account);

                isLoggingIn = false;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                isLoggingIn = false;
            }
        }
    }

    private static void userMenu(User user){
        boolean isUserMenuRunning = true;

        while (isUserMenuRunning) {
            AchievementsManager.checkActions(user);

            System.out.printf("""
                    ----------------- %s -----------------
                    Nivel: %d
                    XP: %d / %d
                    Associacao: %s
                    
                    Conquistas: %d
                        Lixos Reportados: %d
                        Lixos Coletados: %d
                    
                    Participando de %d eventos
                    --------------------------------------
                    """, user.getName(), user.getLevel(), user.getXp(),100 * user.getLevel(), user.getAssociation() == null ? "Nenhuma" : user.getAssociation(),
                    user.getAchievements().size() , user.getTrashReported(), user.getTrashCollected(), user.getEvents().size());

            System.out.print("""
                    1. Relatar Lixo
                    2. Participar de evento
                    3. Associar-se a Organizacao
                    4. Ver achievements
                    5. Sair da Organizacao Associada
                    6. Logout
                    >>\s""");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> {
                        System.out.print("Informe o local do lixo:\n>> ");
                        String location = scanner.nextLine();

                        System.out.print("""
                                Qual a gravidade do lixo?
                                1. Baixa
                                2. Media
                                3. Alta
                                >>\s""");
                        int severity = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("O lixo reportado foi coletado?(y/N)\n>> ");
                        boolean wasCollected = scanner.nextLine().equalsIgnoreCase("y");
                        user.reportTrash(location,severity, wasCollected);

                        System.out.printf("""
                                Lixo reportado!
                                Local: %s
                                Gravidade: %s
                                """, location, severity == 1 ? "Baixa" : severity == 2 ? "Media" : "Alta");
                    }
                    case 2 -> {
                        System.out.println("Para qual evento deseja se inscrever?");
                        List<Event> allEvents = EventManager.getEvents();
                        if(!allEvents.isEmpty()){
                            System.out.print(">> ");
                            int eventIndex = scanner.nextInt();
                            user.joinEvent(allEvents.get(eventIndex));

                            System.out.println("Voce se inscreveu para o evento: " + allEvents.get(eventIndex));
                        }else{
                            System.out.println("Parece que nenhum evento esta marcado...");
                        }
                    }
                    case 3 -> {
                        List<Organization> organizations = AccountManager.getOrganizations();
                        if(!organizations.isEmpty()){
                            System.out.println("Qual a organizacao que deseja se associar?");

                            for (int i = 0; i < organizations.size(); i++) {
                                System.out.println((i + 1) + ". " + organizations.get(i) + "(Sediada em: " + organizations.get(i).getLocation() + ")");
                            }

                            System.out.print(">> ");
                            user.setAssociation(organizations.get(scanner.nextInt() - 1));
                            scanner.nextLine();

                            System.out.println("Voce se associou com: " + user.getAssociation());
                        }else{
                            System.out.println("Parece que nenhuma Organizacao esta disponivel no momento...");
                        }
                    }
                    case 4 -> {
                        List<Achievement> achievements = user.getAchievements();
                        if(achievements.isEmpty()){
                            System.out.println("Parece que voce ainda nao tem nenhum Achievement... Esta na hora de ir " +
                                    "atras de alguns! ");
                        }else{
                            for(Achievement achievement: achievements){
                                System.out.printf("""
                                    ----------- %s -----------
                                    '%s'
                                    
                                    Level: %d
                                    """, achievement.getName(), achievement.getDescription(), achievement.getAchievementLevel());
                            }
                        }
                    }
                    case 5 -> {
                        System.out.println("Voce tem certeza que deseja sair da " + user.getAssociation() + "? (n/Y)");
                        if(scanner.nextLine().equalsIgnoreCase("n")){
                            System.out.println("Cancelando operacao... voce continua associado com " + user.getAssociation());
                        }else{
                            Organization association = user.getAssociation();
                            user.setAssociation(null);
                            System.out.println("Pronto, voce nao esta mais associado com '" + association + "'");
                        }
                    }
                    case 6 -> isUserMenuRunning = false;
                    default -> System.out.println("Opcao invalida!");
                }
            } catch (Exception e) {
                System.out.println("Something went wrong!");
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear the scanner buffer
            }
        }
    }

    private static void organizationMenu(Organization organization){
        boolean isOrgMenuRunning = true;

        while (isOrgMenuRunning) {
            System.out.print("""
                    1. Alertas
                    2. Criar eventos
                    3. Ver eventos
                    4. Logout
                    >>\s""");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> {
                        List<Alert> alerts = organization.getAlerts();
                        if(!alerts.isEmpty()){
                            for(int i = 0; i < alerts.size(); i++){
                                System.out.println("Numero do alerta: " + (i + 1));
                                System.out.println(alerts.get(i));
                            }

                            System.out.println("Insira o numero de um alerta para marca-lo como concluido(Opcional):");
                            System.out.print(">> ");
                            String alertChoice = scanner.nextLine();
                            int index;
                            try {
                                index = Integer.parseInt(alertChoice);
                            }catch (Exception e){
                                index = 0;
                            }

                            if(index != 0){
                                organization.removeAlert(alerts.get(index - 1));
                                System.out.println("Alerta marcado como concluido");
                            }

                        }else{
                            System.out.println("Nenhum alerta registrado");
                        }
                    }
                    case 2 -> {

                    }
                    case 3 -> {

                    }
                    case 4 -> isOrgMenuRunning = false;
                    default -> System.out.println("Opcao invalida!");
                }
            } catch (Exception e) {
                System.out.println("Something went wrong!");
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear the scanner buffer
            }
        }
    }


}
