//
//  TeamListViewController.swift
//  tournenizer
//
//  Created by Ankush Rayabhari on 2/16/18.
//  Copyright © 2018 Ankush Rayabhari. All rights reserved.
//


import UIKit;
import PureLayout;

class TeamListViewController : UITableViewController {
    var teams: [Team] = [];
    let cellIdentifier = "TeamCell";
    let cellSpacingHeight: CGFloat = 5;

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated);
    }

    override func loadView() {
        super.loadView();

        view.backgroundColor = Constants.color.lightGray;
        tableView.allowsSelection = true;
        tableView.separatorStyle = .none;
        tableView.register(TeamCellView.self, forCellReuseIdentifier: cellIdentifier);
        tableView.rowHeight = UITableViewAutomaticDimension;
        tableView.estimatedRowHeight = 50;
    }

    func setTeams(_ teams: [Team]) {
        self.teams = teams;
    }

    // Ensures that the corresponding methods are only called once
    var didUpdateConstraints = false;

    // Sets constraints on all views
    override func updateViewConstraints() {
        if(!didUpdateConstraints) {
            tableView.autoPinEdgesToSuperviewEdges();
            didUpdateConstraints = true;
        }

        super.updateViewConstraints();
    }

    override func numberOfSections(in tableView: UITableView) -> Int {
        return teams.count;
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 1;
    }

    override func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return cellSpacingHeight;
    }

    override func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let headerView = UIView();
        headerView.backgroundColor = UIColor.clear;
        return headerView;
    }

    var cb: ((Team) -> Void)?;

    func setSelectCallback(_ cb: @escaping ((Team) -> Void)) {
        self.cb = cb;
    }

    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        cb?(teams[indexPath.section]);

        if(cb == nil) {
            let vc = TeamViewController();
            vc.setTeam(teams[indexPath.section]);
            self.navigationController?.pushViewController(vc, animated: true);
        }
    }

    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell: TeamCellView? = tableView.dequeueReusableCell(withIdentifier: cellIdentifier) as? TeamCellView;
        cell?.setTeam(teams[indexPath.section]);
        cell?.setNeedsUpdateConstraints();
        cell?.updateConstraintsIfNeeded();

        if(cell != nil) {
            return cell!;
        } else {
            return UITableViewCell();
        }
    }
}
