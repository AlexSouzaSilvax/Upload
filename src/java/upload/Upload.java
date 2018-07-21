package upload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
public class Upload {

    private UploadedFile file;
    private String caminho;

    public void upload(FileUploadEvent evento) {

        FacesContext context = FacesContext.getCurrentInstance();

        String nome = evento.getFile().getFileName();
        String tipo = evento.getFile().getContentType();
        try {
            UploadedFile arquivoUpload = evento.getFile();
            Path arquivoTemp = Files.createTempFile(null, null);
            Files.copy(arquivoUpload.getInputstream(), arquivoTemp, StandardCopyOption.REPLACE_EXISTING);
            setCaminho(arquivoTemp.toString());
            // System.out.println("Caminho da img: " + caminho);
            Path origem = Paths.get(getCaminho());
            Path destino = Paths.get("C:/temp/uploads/" + nome);
            Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
            context.addMessage(null, new FacesMessage(FacesMessage.FACES_MESSAGES, "Sucesso\nUpload feito!"));
            System.out.println("Sucesso\nUpload feito!");
        } catch (IOException erro) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao tentar realizar upload do arquivo!", "" + erro));
            System.out.println("Erro ao tentar realizar upload do arquivo!" + erro);
            erro.printStackTrace();
        }
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}
