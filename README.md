# CampusConnect – DevOps Lab Mini Project

Spring Boot event-registration portal with a full DevOps workflow:
Git/GitHub -> Maven -> JUnit + Selenium -> Jenkins -> Docker -> Kubernetes (Minikube) -> Monitoring.

NOTE: this code was written but NOT compiled/run by its author's tooling. Run the steps below on your own
machine; if something fails, fix it and use the real output for your screenshots.

## 0. Prerequisites (Ubuntu)
    sudo apt update
    sudo apt install -y git openjdk-17-jdk maven curl
    # Google Chrome (for Selenium), Docker, Minikube + kubectl, Jenkins -> install as taught in lab
    java -version ; mvn -version ; docker --version ; kubectl version --client ; minikube version

## 1. Run the app (Fig 2)
    mvn clean package
    java -jar target/campusconnect-1.0.0.jar
    -> open http://localhost:8080   (Events page)  | /register | /organizer | /actuator/health (Fig 11)

## 2. Git + GitHub (Fig 3)
    git init && git add . && git commit -m "Initial CampusConnect project"
    git checkout -b feature/registration     # make a small change, commit it
    git push -u origin feature/registration  # create the repo on GitHub first; open a Pull Request and merge it
    git checkout main && git merge feature/registration && git push origin main

## 3. Maven build (Fig 4)
    mvn clean package          # screenshot the BUILD SUCCESS lines

## 4. Tests (Fig 5)
    mvn test                   # unit tests (JUnit)
    mvn verify -Pselenium      # Selenium UI tests (needs Chrome); screenshot the "Tests run: 4 ... Failures: 0"

## 5. Docker (Fig 7)
    docker build -t campusconnect:1.0 .
    docker images | grep campusconnect
    docker run -d -p 8081:8080 --name cc campusconnect:1.0
    docker ps                  # screenshot docker images + docker ps; then: docker stop cc && docker rm cc

## 6. Kubernetes with Minikube (Fig 8, 10)
    minikube start
    eval $(minikube docker-env)              # so the cluster can see locally built images
    docker build -t campusconnect:1.0 .      # build again inside Minikube's Docker
    kubectl apply -f k8s/
    kubectl get pods,svc                     # Fig 8 (wait until pods are 1/1 Running)
    minikube service campusconnect-svc --url # open this URL in the browser -> Fig 10 (register someone)

## 7. Jenkins pipeline (Fig 6, 9)
 1. Install Jenkins; add plugins: Git, Pipeline, Docker Pipeline, GitHub Integration.
 2. Give the `jenkins` user access:  sudo usermod -aG docker jenkins ; install maven + kubectl on the Jenkins machine;
    copy the minikube kube-config for the jenkins user. Restart Jenkins.
 3. New Item -> Pipeline -> "Pipeline script from SCM" -> your GitHub repo URL -> Script Path: Jenkinsfile.
    (Edit the repo URL inside Jenkinsfile first.)
 4. Optional: GitHub webhook -> http://<your-ip>:8080/github-webhook/  and enable "GitHub hook trigger".
 5. Build Now. Fig 6 = Stage View (all green). Fig 9 = Console Output end showing "Finished: SUCCESS".

## Screenshot checklist
 Fig 2 home page | Fig 3 GitHub repo/PR | Fig 4 mvn BUILD SUCCESS | Fig 5 test results | Fig 6 Jenkins stage view
 Fig 7 docker images + ps | Fig 8 kubectl get pods,svc | Fig 9 Jenkins console SUCCESS | Fig 10 app via minikube URL
 Fig 11 /actuator/health -> {"status":"UP"}

## Differences from the printed report snippets (update the report to match what you really ran)
 - Selenium test class is RegistrationUiIT (run with -Pselenium via Failsafe).
 - deployment.yaml has imagePullPolicy: Never (image built inside Minikube).
 - Jenkins "Docker Build" stage builds inside minikube's Docker daemon.
Event registration module with duplicate check
Event registration module with duplicate check
