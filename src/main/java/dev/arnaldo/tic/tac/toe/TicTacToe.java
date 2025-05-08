package dev.arnaldo.tic.tac.toe;

import com.jaoow.sql.connector.SQLConnector;
import com.jaoow.sql.connector.type.impl.SQLiteDatabaseType;
import com.jaoow.sql.executor.SQLExecutor;
import dev.arnaldo.tic.tac.toe.repository.match.MatchRepository;
import dev.arnaldo.tic.tac.toe.repository.match.impl.MatchRepositoryImpl;
import dev.arnaldo.tic.tac.toe.repository.move.MoveRepository;
import dev.arnaldo.tic.tac.toe.repository.move.impl.MoveRepositoryImpl;
import dev.arnaldo.tic.tac.toe.repository.user.UserRepository;
import dev.arnaldo.tic.tac.toe.repository.user.impl.UserRepositoryImpl;
import dev.arnaldo.tic.tac.toe.service.MatchService;
import dev.arnaldo.tic.tac.toe.service.UserService;
import dev.arnaldo.tic.tac.toe.service.impl.MatchServiceImpl;
import dev.arnaldo.tic.tac.toe.service.impl.UserServiceImpl;
import dev.arnaldo.tic.tac.toe.util.ResourceUtil;
import dev.arnaldo.tic.tac.toe.view.HomeView;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@Getter
public class TicTacToe {

    private static final String PATH = System.getProperty("user.dir");
    private static final String DB_PATH = PATH + File.separator + "data" + File.separator + "tic-tac-toe.db";

    @Getter
    private static TicTacToe instance;

    private SQLConnector connector;
    private SQLExecutor executor;

    private UserRepository userRepository;
    private MatchRepository matchRepository;
    private MoveRepository moveRepository;

    private UserService userService;
    private MatchService matchService;

    @SneakyThrows
    protected TicTacToe() {
        instance = this;

        this.initSQLConnection();
        this.initSQLExecutor();
        this.initSQLScript();
        this.initRepositories();
        this.initServices();

        new HomeView(null);
    }

    @SneakyThrows
    private void initSQLConnection() {
        this.connector = SQLiteDatabaseType.builder()
                .file(new File(DB_PATH))
                .build()
                .connect();
    }

    private void initSQLExecutor() {
        this.executor = new SQLExecutor(this.connector);
    }

    private void initSQLScript() {
        log.info("Executing SQL script...");
        this.connector.execute(connection -> {
            try(InputStreamReader reader = ResourceUtil.getInputStreamReader("/tic-tac-toe.sql")) {
                final var runner = new ScriptRunner(connection);

                runner.setSendFullScript(false);
                runner.setStopOnError(true);
                runner.runScript(reader);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        log.info("SQL script executed successfully.");
    }

    private void initRepositories() {
        log.info("Initializing repositories...");

        this.userRepository = new UserRepositoryImpl(this.executor);
        this.matchRepository = new MatchRepositoryImpl(this.executor, this.userRepository);
        this.moveRepository = new MoveRepositoryImpl(this.executor, this.userRepository, this.matchRepository);
    }

    private void initServices() {
        log.info("Initializing services...");

        this.userService = new UserServiceImpl(this.userRepository);
        this.matchService = new MatchServiceImpl(this.userRepository, this.matchRepository, this.moveRepository);
    }

}
